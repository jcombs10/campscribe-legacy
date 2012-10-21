package com.campscribe.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.auth.CampScribeUser;
import com.campscribe.business.ClazzManager;
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.StaffManager;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

@Controller
public class TrackingController {

	protected final Log logger = LogFactory.getLog(getClass());

	private EventManager eventMgr;
	private ClazzManager clazzMgr;
	private MeritBadgeManager mbMgr;
	private StaffManager staffMgr;

	@RequestMapping(value="/tracking.cs", method=RequestMethod.POST)
	public ModelAndView getTracking(@ModelAttribute("eventFilter")
	EventFilterFBO fbo, BindingResult result, HttpServletRequest request) throws ServletException, IOException {
		
		return getTracking(fbo, request);
	}
	
	@RequestMapping(value="/tracking.cs", method=RequestMethod.GET)
	public ModelAndView getTracking(HttpServletRequest request)
			throws ServletException, IOException {
	
		List<Event> events = getEventManager().listEvents();

		EventFilterFBO fbo = new EventFilterFBO();
		fbo.setEventId(EventUtil.findCurrentEventId(events));
		
		return getTracking(fbo, request);
	}

	private ModelAndView getTracking(EventFilterFBO fbo, HttpServletRequest request)
			throws ServletException, IOException {
		logger.info("Returning tracking view");

		ModelAndView mav = new ModelAndView("tracking.jsp");

		List<Event> events = getEventManager().listEvents();
		mav.addObject("eventList", events);

		mav.addObject("eventFilter", fbo);

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CampScribeUser) {
			CampScribeUser user = (CampScribeUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = user.getUsername(); //get logged in username
			Staff s = getStaffManager().getStaffByName(name);

			mav.addObject("fullName", s.getName());
			mav.addObject("programArea", s.getProgramArea());

			if (request.isUserInRole("area_director")) {
				if (fbo!=null && fbo.getEventId()!=null) {
					mav.addObject("clazzes", getEventManager().getClazzesByProgramArea(new Key<Event>(Event.class, fbo.getEventId()), s.getProgramArea()));
				} else {
					mav.addObject("clazzes", getEventManager().getClazzesByProgramArea(s.getProgramArea()));
				}
			} else if (request.isUserInRole("counselor")) {
				if (fbo!=null && fbo.getEventId()!=null) {
					mav.addObject("clazzes", getEventManager().getClazzesByCounselor(new Key<Event>(Event.class, fbo.getEventId()), new Key<Staff>(Staff.class, s.getId())));
				} else {
					mav.addObject("clazzes", getEventManager().getClazzesByCounselor(new Key<Staff>(Staff.class, s.getId())));
				}
			} else {
				if (fbo!=null && fbo.getEventId()!=null) {
					mav.addObject("clazzes", getClazzManager().listClazzes(new Key<Event>(Event.class, fbo.getEventId())));
				} else {
					mav.addObject("clazzes", getClazzManager().listClazzes());
				}
			}
		} else {
			//not a campscribe user, must be the system admin
			if (fbo!=null && fbo.getEventId()!=null) {
				mav.addObject("clazzes", getClazzManager().listClazzes(new Key<Event>(Event.class, fbo.getEventId())));
			} else {
				mav.addObject("clazzes", getClazzManager().listClazzes());
			}
		}

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

	private ClazzManager getClazzManager() {
		if (clazzMgr == null) {
			clazzMgr = new ClazzManager();
		}
		return clazzMgr;
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
