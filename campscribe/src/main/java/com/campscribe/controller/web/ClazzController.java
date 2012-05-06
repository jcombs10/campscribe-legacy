package com.campscribe.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.ClazzManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.StaffManager;
import com.campscribe.client.Event;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.MeritBadge;
import com.campscribe.model2.Scout;
import com.campscribe.model2.Staff;
import com.googlecode.objectify.Key;

@Controller
public class ClazzController {

	protected final Log logger = LogFactory.getLog(getClass());
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
	        mav.addObject("clazz", getClazzManager().getClazz(cKey));
	        mav.addObject("scoutLookup", getScoutLookup());
	        mav.addObject("staffLookup", getStaffLookup());
	        mav.addObject("mbLookup", getMbLookup());
	        
	        return mav;
	    }

	private Map<Long, Scout> getScoutLookup() {
		Map<Long, Scout> scoutLookup = new HashMap<Long, Scout>();
		for(Scout s:getScoutManager().listScouts()) {
			scoutLookup.put(s.getId(), s);
		}
		return scoutLookup;
	}

	private Map<Long, Staff> getStaffLookup() {
		Map<Long, Staff> staffLookup = new HashMap<Long, Staff>();
		for(Staff s:getStaffManager().listStaff()) {
			staffLookup.put(s.getId(), s);
		}
		return staffLookup;
	}

	private Map<Long, MeritBadge> getMbLookup() {
		Map<Long, MeritBadge> mbLookup = new HashMap<Long, MeritBadge>();
		for(MeritBadge s:getMeritBadgeManager().listMeritBadges()) {
			mbLookup.put(s.getId(), s);
		}
		return mbLookup;
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
