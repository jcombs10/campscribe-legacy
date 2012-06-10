package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.BadgeCompletionChecker;
import com.campscribe.business.ClazzManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.Clazz.Note;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.campscribe.model.TrackProgress.RequirementCompletion;
import com.campscribe.shared.NoteDTO;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.campscribe.shared.TrackProgressDTO.DateAttendanceDTO;
import com.campscribe.shared.TrackProgressDTO.RequirementCompletionDTO;
import com.campscribe.shared.TrackProgressWrapperDTO;
import com.googlecode.objectify.Key;

@Controller
public class TrackingServiceController {

//	@Autowired
	ClazzManager clazzMgr = new ClazzManager();
	MeritBadgeManager mbMgr = new MeritBadgeManager();
	ScoutManager scoutMgr = new ScoutManager();
	TrackProgressManager tpMgr = new TrackProgressManager();

	@RequestMapping(method=RequestMethod.GET, value = "/events/{eventId}/classes/{clazzId}/progress",headers="Accept=application/json")
	public @ResponseBody TrackProgressWrapperDTO getClazzTracking(@PathVariable Long eventId, @PathVariable Long clazzId) {
		TrackProgressWrapperDTO wrapper = new TrackProgressWrapperDTO();
		
		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Clazz c = clazzMgr.getClazz(new Key<Clazz>(eKey, Clazz.class, clazzId));
		
		List<NoteDTO> notesList = new ArrayList<NoteDTO>();
		for (Note n:c.getNotesList()) {
			NoteDTO nDTO = new NoteDTO();
			nDTO.setDate(n.getDate());
			nDTO.setStaffName(n.getStaffName());
			nDTO.setNoteText(n.getNoteText());
			notesList.add(nDTO);
		}
		wrapper.setNotesList(notesList);
		wrapper.setComments("");
		
		Key<Event> eventKey = new Key<Event>(Event.class, eventId);
		List<TrackProgress> trackingList = tpMgr.getTrackingForClazz(new Key<Clazz>(eventKey, Clazz.class, clazzId));
		List<TrackProgressDTO> retList = new ArrayList<TrackProgressDTO>();
		for (TrackProgress tp:trackingList) {
			TrackProgressDTO tpDTO = new TrackProgressDTO();
			tpDTO.setId(tp.getId());
			
			Scout scout = scoutMgr.getScout(tp.getScoutKey().getId());
			ScoutDTO scoutDTO = new ScoutDTO();
			scoutDTO.setId(tp.getScoutKey().getId());
			scoutDTO.setFirstName(scout.getFirstName());
			scoutDTO.setLastName(scout.getLastName());
			tpDTO.setScout(scoutDTO);
			
			List<DateAttendanceDTO> attendanceList = new ArrayList<DateAttendanceDTO>();
			for (DateAttendance da:tp.getAttendanceList()) {
				DateAttendanceDTO daDTO = new DateAttendanceDTO();
				daDTO.setDate(da.getDate());
				daDTO.setPresent(da.isPresent());
				attendanceList.add(daDTO);
			}
			tpDTO.setAttendanceList(attendanceList);
			
			List<RequirementCompletionDTO> reqList = new ArrayList<RequirementCompletionDTO>();
			for (RequirementCompletion rc:tp.getRequirementList()) {
				RequirementCompletionDTO rcDTO = new RequirementCompletionDTO();
				rcDTO.setCompleted(rc.isCompleted());
				rcDTO.setReqNumber(rc.getReqNumber());
				reqList.add(rcDTO);
			}
			tpDTO.setRequirementList(reqList);
			
			retList.add(tpDTO);
		}
		
		Collections.sort(retList, new Comparator<TrackProgressDTO>(){

			@Override
			public int compare(TrackProgressDTO o1, TrackProgressDTO o2) {
				if (o1.getScout().getLastName().equalsIgnoreCase(o2.getScout().getLastName())) {
					return o1.getScout().getFirstName().compareToIgnoreCase(o2.getScout().getFirstName());
				}
				return o1.getScout().getLastName().compareToIgnoreCase(o2.getScout().getLastName());
			}
			
		});
		
		wrapper.setTrackingList(retList);
		return wrapper;
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/events/{eventId}/classes/{clazzId}/progress",headers="Accept=application/json")
	public @ResponseBody TrackProgressWrapperDTO updateClazzTracking(@PathVariable Long eventId, @PathVariable Long clazzId, @RequestBody TrackProgressWrapperDTO trackers) {
		
		for (TrackProgressDTO trackerDTO:trackers.getTrackingList()) {
			TrackProgress tracker = tpMgr.get(new Key<TrackProgress>(TrackProgress.class, trackerDTO.getId()));
			List<DateAttendanceDTO> attDTOList = trackerDTO.getAttendanceList();
			List<RequirementCompletionDTO> reqDTOList = trackerDTO.getRequirementList();
			
			List<DateAttendance> attList = tracker.getAttendanceList();
			for (int i=0; i<attDTOList.size(); i++) {
				attList.get(i).setPresent(attDTOList.get(i).isPresent());
			}
			
			List<RequirementCompletion> reqList = tracker.getRequirementList();
			for (int i=0; i<reqDTOList.size(); i++) {
				reqList.get(i).setCompleted(reqDTOList.get(i).isCompleted());
			}

			Clazz c = clazzMgr.getClazz(tracker.getClazzKey());
			boolean complete = BadgeCompletionChecker.isComplete(mbMgr.getMeritBadge(c.getMbId().getId()).getRequirements(), tracker.getRequirementList());
			tracker.setComplete(complete);
			
			tpMgr.update(tracker);

		}
		
		if (trackers.getComments()!=null && trackers.getComments().trim().length() > 0) {
			clazzMgr.updateComments(eventId, clazzId, trackers.getComments());
		}
		
		return trackers;
	}

}
