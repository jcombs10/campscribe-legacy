package com.campscribe.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.campscribe.business.EventManager;
import com.campscribe.business.MeritBadgeManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.business.StaffManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.model.BadgeCompletionChecker;
import com.campscribe.model.Clazz;
import com.campscribe.model.ClazzComparator;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.campscribe.model.TrackProgress;
import com.googlecode.objectify.Key;

@Controller
public class ReportsController {

	protected final Log logger = LogFactory.getLog(getClass());

	private EventManager eventMgr;
	private TrackProgressManager tpMgr;
	private MeritBadgeManager mbMgr;
	private ScoutManager scoutMgr;
	private StaffManager staffMgr;

	@RequestMapping(value="/reports.cs", method=RequestMethod.POST)
	public ModelAndView getTracking(@ModelAttribute("reportFilter")
	ReportFilterFBO fbo, BindingResult result, HttpServletRequest request) throws ServletException, IOException {

		return getTracking(fbo, request);
	}

	@RequestMapping(value="/reports.cs", method=RequestMethod.GET)
	public ModelAndView getTracking(HttpServletRequest request)
			throws ServletException, IOException {

		List<Event> events = getEventManager().listEvents();

		ReportFilterFBO fbo = new ReportFilterFBO();
		fbo.setEventId(EventUtil.findCurrentEventId(events));

		fbo.setProgramArea("ALL");
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CampScribeUser) {
			CampScribeUser user = (CampScribeUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = user.getUsername(); //get logged in username
			Staff s = getStaffManager().getStaffByName(name);
			if (StringUtils.isNotEmpty(s.getProgramArea())) {
				fbo.setProgramArea(s.getProgramArea());
			}
		}

		return getTracking(fbo, request);
	}

	private ModelAndView getTracking(ReportFilterFBO fbo, HttpServletRequest request)
			throws ServletException, IOException {
		logger.info("Returning tracking view");

		ModelAndView mav = new ModelAndView("reports.jsp");

		List<Event> events = getEventManager().listEvents();
		mav.addObject("eventList", events);

		mav.addObject("reportFilter", fbo);

		Map<Long, String> completionLookup = new HashMap<Long, String>();
		TreeMap<String, TreeMap<Clazz,ArrayList<TrackProgress>>> clazzByProgramAreaMap = new TreeMap<String, TreeMap<Clazz,ArrayList<TrackProgress>>>();
		List<Clazz> clazzList = null;
		if (fbo.getProgramArea()==null || "ALL".equals(fbo.getProgramArea())) {
			clazzList = getEventManager().getClazzesByEvent(new Key<Event>(Event.class, fbo.getEventId()));
		} else {
			clazzList = getEventManager().getClazzesByProgramArea(new Key<Event>(Event.class, fbo.getEventId()), fbo.getProgramArea());
		}
		for (Clazz c:clazzList) {
			if (!clazzByProgramAreaMap.containsKey(c.getProgramArea())) {
				TreeMap<Clazz,ArrayList<TrackProgress>> trackingMap = new TreeMap<Clazz,ArrayList<TrackProgress>>(new ClazzComparator());
				clazzByProgramAreaMap.put(c.getProgramArea(), trackingMap);
			}
			List<TrackProgress> trackingList = getTrackProgressManager().getTrackingForClazz(new Key<Clazz>(new Key<Event>(Event.class, fbo.getEventId()), Clazz.class, c.getId()));
			for (TrackProgress tp:trackingList) {
				if (!clazzByProgramAreaMap.get(c.getProgramArea()).containsKey(c)) {
					ArrayList<TrackProgress> scoutList = new ArrayList<TrackProgress>();
					clazzByProgramAreaMap.get(c.getProgramArea()).put(c, scoutList);
				}
//				System.out.println("checking completion for clazz "+c.getMbName()+", trackerId "+tp.getId());
				completionLookup.put(tp.getId(), (BadgeCompletionChecker.isComplete(getMeritBadgeManager().getMeritBadge(c.getMbId().getId()).getRequirements(), tp.getRequirementList())?"Complete":"Partial"));
				clazzByProgramAreaMap.get(c.getProgramArea()).get(c).add(tp);
			}
		}

		mav.addObject("clazzByProgramAreaMap", clazzByProgramAreaMap);
		mav.addObject("completionLookup", completionLookup);
		mav.addObject("scoutLookup", getScoutLookup(new Key<Event>(Event.class, fbo.getEventId())));
		mav.addObject("staffLookup", getStaffLookup());
		return mav;
	}

	private Map<Key<Scout>, Scout> getScoutLookup(Key<Event> eKey) {
		Map<Key<Scout>, Scout> scoutLookup = new HashMap<Key<Scout>, Scout>();
		for(Scout s:getScoutManager().listScouts(eKey)) {
			scoutLookup.put(new Key<Scout>(Scout.class, s.getId()), s);
		}
		return scoutLookup;
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

	private TrackProgressManager getTrackProgressManager() {
		if (tpMgr == null) {
			tpMgr = new TrackProgressManager();
		}
		return tpMgr;
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

}
