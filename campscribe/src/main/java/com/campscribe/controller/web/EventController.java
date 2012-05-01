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

import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.StaffManager;
import com.campscribe.model2.MeritBadge;
import com.campscribe.model2.Staff;

@Controller
public class EventController {

	protected final Log logger = LogFactory.getLog(getClass());
	private EventManager eventMgr;
	private MeritBadgeManager mbMgr;
	private StaffManager staffMgr;
	
	@RequestMapping("/events.cs")
	public ModelAndView listEvents()
	            throws ServletException, IOException {

	        logger.info("Returning events view");

	        ModelAndView mav = new ModelAndView("events.jsp");
	        mav.addObject("events", getEventManager().listEvents());
	        
	        return mav;
	    }

	@RequestMapping("/deleteEvent.cs")
	public ModelAndView deleteEvent(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning events view");

	        getEventManager().deleteEvent(id);
	        
	        ModelAndView mav = new ModelAndView("events.jsp");
	        mav.addObject("events", getEventManager().listEvents());
	        
	        return mav;
	    }

	@RequestMapping("/viewEvent.cs")
	public ModelAndView viewEvent(@RequestParam("id") long eventId)
	            throws ServletException, IOException {

	        logger.info("Returning event view");

	        ModelAndView mav = new ModelAndView("viewEvent.jsp");
	        mav.addObject("event", getEventManager().getEvent(eventId));
	        mav.addObject("mbLookup", getMbNameLookup());
	        mav.addObject("staffLookup", getStaffLookup());
	        
	        return mav;
	    }

	private Map<Long, MeritBadge> getMbNameLookup() {
		Map<Long, MeritBadge> mbLookup = new HashMap<Long, MeritBadge>();
		for(MeritBadge mb:getMeritBadgeManager().listMeritBadges()) {
			mbLookup.put(mb.getId(), mb);
		}
		return mbLookup;
	}

	private Map<Long, Staff> getStaffLookup() {
		Map<Long, Staff> staffLookup = new HashMap<Long, Staff>();
		for(Staff s:getStaffManager().listStaff()) {
			staffLookup.put(s.getId(), s);
		}
		return staffLookup;
	}

	private EventManager getEventManager() {
		if (eventMgr == null) {
			eventMgr = new EventManager();
		}
		return eventMgr;
	}

	private MeritBadgeManager getMeritBadgeManager() {
		if (mbMgr == null) {
			mbMgr = new MeritBadgeManager();
		}
		return mbMgr;
	}

	private StaffManager getStaffManager() {
		if (staffMgr == null) {
			staffMgr = new StaffManager();
		}
		return staffMgr;
	}

}
