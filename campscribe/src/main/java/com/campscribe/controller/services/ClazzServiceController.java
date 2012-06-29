package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.ScoutDTO;
import com.googlecode.objectify.Key;

@Controller
public class ClazzServiceController {

//	@Autowired
	EventManager eventMgr = new EventManager();
//	@Autowired
	MeritBadgeManager mbMgr = new MeritBadgeManager();
//	@Autowired
	ClazzManager clazzMgr = new ClazzManager();

	@RequestMapping(method=RequestMethod.POST, value = "/events/{id}/classes/",headers="Accept=application/json")
	public @ResponseBody ClazzDTO addClazzToEvent(@PathVariable Long id, @RequestBody ClazzDTO clazzDTO) {
		System.err.println("addClazz called");
		Clazz c = new Clazz(clazzDTO.getDescription(), new Key<MeritBadge>(MeritBadge.class, clazzDTO.getMbId()));
		c.setStaffId(new Key<Staff>(Staff.class, clazzDTO.getStaffId()));
		c.setProgramArea(clazzDTO.getProgramArea());
		c.setMbName(mbMgr.getMeritBadge(clazzDTO.getMbId()).getBadgeName());
		
		eventMgr.addClazz(id, c);
		return clazzDTO;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/events/{eventId}/classes/{clazzId}",headers="Accept=application/json")
	public @ResponseBody void addScoutToClazz(@PathVariable Long eventId, @PathVariable Long clazzId, @RequestBody Long[] scoutList) {
		System.err.println("addScoutToClazz called");

		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Key<Clazz> cKey = new Key<Clazz>(eKey, Clazz.class, clazzId);
		List<Key<Scout>> scoutKeyList = new ArrayList<Key<Scout>>();
		for (Long l:scoutList) {
			scoutKeyList.add(new Key<Scout>(Scout.class, l));
		}
		clazzMgr.addScoutsToClazz(cKey, scoutKeyList );
	}

	@RequestMapping(method=RequestMethod.POST, value = "/events/{eventId}/classes/{clazzId}/deleteScout",headers="Accept=application/json")
	public @ResponseBody void deleteScoutFromClazz(@PathVariable Long eventId, @PathVariable Long clazzId, @RequestBody ScoutDTO droppedScout) {
		System.err.println("deleteScoutFromClazz called");

		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Key<Clazz> cKey = new Key<Clazz>(eKey, Clazz.class, clazzId);
		clazzMgr.deleteScoutFromClazz(cKey, new Key<Scout>(Scout.class, droppedScout.getId()) );
	}

}
