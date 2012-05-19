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
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

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
	        Event e = getEventManager().getEvent(eventId);
	        mav.addObject("event", e);
	        mav.addObject("clazzes", getEventManager().getClazzes(e.getClazzes()));
	        mav.addObject("mbLookup", getMbNameLookup());
	        mav.addObject("staffLookup", getStaffLookup());
	        
	        return mav;
	    }

	@RequestMapping("/deleteClazz.cs")
	public ModelAndView deleteClazz(@RequestParam("id") long id)
	            throws ServletException, IOException {

	        logger.info("Returning clazzes view");

	        long eventId = getEventManager().getEventForClazz(id).getId();
	        getEventManager().deleteClazz(id);
	        
	        ModelAndView mav = new ModelAndView("viewEvent.jsp");
	        Event e = getEventManager().getEvent(eventId);
	        mav.addObject("event", e);
	        mav.addObject("clazzes", getEventManager().getClazzes(e.getClazzes()));
	        mav.addObject("mbLookup", getMbNameLookup());
	        mav.addObject("staffLookup", getStaffLookup());
	        
	        return mav;
	    }

	private Map<Key<MeritBadge>, MeritBadge> getMbNameLookup() {
		Map<Key<MeritBadge>, MeritBadge> mbLookup = new HashMap<Key<MeritBadge>, MeritBadge>();
		for(MeritBadge mb:getMeritBadgeManager().listMeritBadges()) {
			mbLookup.put(new Key<MeritBadge>(MeritBadge.class, mb.getId()), mb);
		}
		return mbLookup;
	}

	private Map<Key<Staff>, Staff> getStaffLookup() {
		Map<Key<Staff>, Staff> staffLookup = new HashMap<Key<Staff>, Staff>();
		for(Staff s:getStaffManager().listStaff()) {
			staffLookup.put(new Key<Staff>(Staff.class, s.getId()), s);
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
