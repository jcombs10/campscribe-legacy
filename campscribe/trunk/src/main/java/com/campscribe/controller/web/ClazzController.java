package com.campscribe.controller.web;

 import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.StaffManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

@Controller
public class ClazzController {

	protected final Log logger = LogFactory.getLog(getClass());
	private EventManager eventMgr;
	private ClazzManager clazzMgr;
	private MeritBadgeManager mbMgr;
	private StaffManager staffMgr;
	private ScoutManager scoutMgr;

	@RequestMapping("/viewClazz.cs")
	public ModelAndView viewClazz(@RequestParam("eventId") long eventId, @RequestParam("clazzId") long clazzId)
			throws ServletException, IOException {

		logger.info("Returning clazz view");

		ModelAndView mav = new ModelAndView("viewClazz.jsp");
		
		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Key<Clazz> cKey = new Key<Clazz>(eKey, Clazz.class, clazzId);
		Clazz c = getClazzManager().getClazz(cKey);
		Map<Key<Scout>, Scout> scoutLookup = getScoutManager().getScouts(c.getScoutIds());

		List<Scout> scoutList = new ArrayList<Scout>(scoutLookup.values());
		Collections.sort(scoutList, new Comparator<Scout>() {

			@Override
			public int compare(Scout o1, Scout o2) {
				if (o1.getLastName().equalsIgnoreCase(o2.getLastName())) {
					if (o1.getFirstName().equalsIgnoreCase(o2.getFirstName())) {
						if (o1.getUnitType().equalsIgnoreCase(o2.getUnitType())) {
							return o1.getUnitNumber().compareToIgnoreCase(o2.getUnitNumber());
						}
						return o1.getUnitType().compareToIgnoreCase(o2.getUnitType());
					}
					return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
				}
				return o1.getLastName().compareToIgnoreCase(o2.getLastName());
			}

		});


		mav.addObject("event", getEventManager().getEvent(eventId));
		mav.addObject("clazz", getClazzManager().getClazz(cKey));
		mav.addObject("scouts", scoutList);
		mav.addObject("staffLookup", getStaffLookup());
		mav.addObject("mbLookup", getMbLookup());

		return mav;
	}

	private Map<Key<Staff>, Staff> getStaffLookup() {
		Map<Key<Staff>, Staff> staffLookup = new HashMap<Key<Staff>, Staff>();
		for(Staff s:getStaffManager().listStaff()) {
			staffLookup.put(new Key<Staff>(Staff.class, s.getId()), s);
		}
		return staffLookup;
	}

	private Map<Key<MeritBadge>, MeritBadge> getMbLookup() {
		Map<Key<MeritBadge>, MeritBadge> mbLookup = new HashMap<Key<MeritBadge>, MeritBadge>();
		for(MeritBadge s:getMeritBadgeManager().listMeritBadges()) {
			mbLookup.put(new Key<MeritBadge>(MeritBadge.class, s.getId()), s);
		}
		return mbLookup;
	}

	private EventManager getEventManager() {
		if (eventMgr == null) {
			eventMgr = new EventManager();
		}
		return eventMgr;
	}

	private ClazzManager getClazzManager() {
		if (clazzMgr == null) {
			clazzMgr = new ClazzManager();
		}
		return clazzMgr;
	}

	private ScoutManager getScoutManager() {
		if (scoutMgr == null) {
			scoutMgr = new ScoutManager();
		}
		return scoutMgr;
	}

	private StaffManager getStaffManager() {
		if (staffMgr == null) {
			staffMgr = new StaffManager();
		}
		return staffMgr;
	}

	private MeritBadgeManager getMeritBadgeManager() {
		if (mbMgr == null) {
			mbMgr = new MeritBadgeManager();
		}
		return mbMgr;
	}

}
