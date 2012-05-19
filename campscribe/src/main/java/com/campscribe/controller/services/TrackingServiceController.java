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

import com.campscribe.business.ScoutManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.client.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.campscribe.shared.TrackProgressDTO.DateAttendanceDTO;
import com.googlecode.objectify.Key;

@Controller
public class TrackingServiceController {

//	@Autowired
	TrackProgressManager tpMgr = new TrackProgressManager();
	ScoutManager scoutMgr = new ScoutManager();

	@RequestMapping(method=RequestMethod.GET, value = "/events/{eventId}/classes/{clazzId}/progress",headers="Accept=application/json")
	public @ResponseBody List<TrackProgressDTO> getClazzTracking(@PathVariable Long eventId, @PathVariable Long clazzId) {
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
		return retList;
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/events/{eventId}/classes/{clazzId}/progress",headers="Accept=application/json")
	public @ResponseBody TrackProgressDTO[] updateClazzTracking(@PathVariable Long eventId, @PathVariable Long clazzId, @RequestBody TrackProgressDTO[] trackers) {
		
		for (TrackProgressDTO trackerDTO:trackers) {
			TrackProgress tracker = tpMgr.get(new Key<TrackProgress>(TrackProgress.class, trackerDTO.getId()));
			List<DateAttendanceDTO> attDTOList = trackerDTO.getAttendanceList();
			List<DateAttendance> attList = tracker.getAttendanceList();
			for (int i=0; i<attDTOList.size(); i++) {
				attList.get(i).setPresent(attDTOList.get(i).isPresent());
			}
			tpMgr.update(tracker);

		}
		
		return trackers;
	}

}
