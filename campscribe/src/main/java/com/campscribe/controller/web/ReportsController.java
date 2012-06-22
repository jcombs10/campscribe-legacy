package com.campscribe.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import com.campscribe.business.ScoutManager;
import com.campscribe.business.StaffManager;
import com.campscribe.business.TrackProgressManager;
import com.campscribe.model.Clazz;
import com.campscribe.model.ClazzComparator;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.campscribe.model.Scout;
import com.campscribe.model.ScoutComparator;
import com.campscribe.model.Staff;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.Unit;
import com.campscribe.model.UnitComparator;
import com.googlecode.objectify.Key;

@Controller
public class ReportsController {

	protected final Log logger = LogFactory.getLog(getClass());

	private EventManager eventMgr;
	private ScoutManager scoutMgr;
	private StaffManager staffMgr;
	private TrackProgressManager tpMgr;

	@RequestMapping(value="/reports.cs", method=RequestMethod.POST)
	public ModelAndView getReport(@ModelAttribute("reportFilter")
	ReportFilterFBO fbo, BindingResult result, HttpServletRequest request) throws ServletException, IOException {

		return getReport(fbo, request);
	}

	@RequestMapping(value="/reports.cs", method=RequestMethod.GET)
	public ModelAndView getReport(HttpServletRequest request)
			throws ServletException, IOException {

		List<Event> events = getEventManager().listEvents();

		ReportFilterFBO fbo = new ReportFilterFBO();
		fbo.setEventId(EventUtil.findCurrentEventId(events));

		fbo.setGroupBy("Program Area");
		fbo.setProgramArea("ALL");
		fbo.setUnit("ALL");
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CampScribeUser) {
			CampScribeUser user = (CampScribeUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = user.getUsername(); //get logged in username
			Staff s = getStaffManager().getStaffByName(name);
			if (StringUtils.isNotEmpty(s.getProgramArea())) {
				fbo.setProgramArea(s.getProgramArea());
			}
		}

		return getReport(fbo, request);
	}

	private ModelAndView getReport(ReportFilterFBO fbo, HttpServletRequest request)
			throws ServletException, IOException {
		logger.info("Returning reports view");

		ModelAndView mav = new ModelAndView("reports.jsp");

		List<Event> events = getEventManager().listEvents();
		mav.addObject("eventList", events);

		Set<Unit> unitSet = new TreeSet<Unit>(new UnitComparator());
		List<Scout> allScoutList = getScoutManager().getScoutsByEvent(new Key<Event>(Event.class, fbo.getEventId()));
		for (Scout s:allScoutList) {
			unitSet.add(new Unit(s.getUnitType(), s.getUnitNumber()));
		}
		mav.addObject("unitSet", unitSet);
		
		mav.addObject("reportFilter", fbo);
		
		Map<Key<Scout>, Scout> scoutLookup = getScoutLookup(new Key<Event>(Event.class, fbo.getEventId()));
		Map<Key<Clazz>, Clazz> clazzLookup = getClazzLookup(new Key<Event>(Event.class, fbo.getEventId()));

		if (fbo.getGroupBy().equals("Program Area")) {
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
					Scout s = scoutLookup.get(tp.getScoutKey());
					String unit = "";
					if (s != null) {
						unit = s.getUnitType()+" "+s.getUnitNumber();
					}
					if ("ALL".equals(fbo.getUnit()) || unit.equals(fbo.getUnit())) {
						if (!clazzByProgramAreaMap.get(c.getProgramArea()).containsKey(c)) {
							ArrayList<TrackProgress> scoutList = new ArrayList<TrackProgress>();
							clazzByProgramAreaMap.get(c.getProgramArea()).put(c, scoutList);
						}
//						System.out.println("checking completion for clazz "+c.getMbName()+", trackerId "+tp.getId());
						clazzByProgramAreaMap.get(c.getProgramArea()).get(c).add(tp);
					}
				}
			}

			mav.addObject("clazzByProgramAreaMap", clazzByProgramAreaMap);
			mav.addObject("scoutLookup", scoutLookup);
		} else {
			TreeMap<String, TreeMap<Scout,ArrayList<TrackProgress>>> scoutByUnitMap = new TreeMap<String, TreeMap<Scout,ArrayList<TrackProgress>>>();
			
			List<Scout> scoutList = null;
			if (fbo.getUnit()==null || "ALL".equals(fbo.getUnit())) {
				scoutList = getScoutManager().getScoutsByEvent(new Key<Event>(Event.class, fbo.getEventId()));
			} else {
				String[] unitParts = fbo.getUnit().split(" ");
				scoutList = getScoutManager().getScoutsByUnit(new Key<Event>(Event.class, fbo.getEventId()), unitParts[0], unitParts[1]);
			}
			for (Scout s:scoutList) {
				String unit = s.getUnitType()+" "+s.getUnitNumber();
				if (!scoutByUnitMap.containsKey(unit)) {
					TreeMap<Scout,ArrayList<TrackProgress>> trackingMap = new TreeMap<Scout,ArrayList<TrackProgress>>(new ScoutComparator());
					scoutByUnitMap.put(unit, trackingMap);
				}

				List<TrackProgress> trackingList = getTrackProgressManager().getTrackingForScout(new Key<Scout>(Scout.class, s.getId()));
				for (TrackProgress tp:trackingList) {
					Clazz c = clazzLookup.get(tp.getClazzKey());
					if ("ALL".equals(fbo.getProgramArea()) || c.getProgramArea().equals(fbo.getProgramArea())) {
						if (!scoutByUnitMap.get(unit).containsKey(s)) {
							ArrayList<TrackProgress> badgeList = new ArrayList<TrackProgress>();
							scoutByUnitMap.get(unit).put(s, badgeList);
						}
						scoutByUnitMap.get(unit).get(s).add(tp);
					}
				}
			}

			mav.addObject("scoutByUnitMap", scoutByUnitMap);
			mav.addObject("clazzLookup", clazzLookup);
		}
		return mav;
	}

	private Map<Key<Scout>, Scout> getScoutLookup(Key<Event> eKey) {
		Map<Key<Scout>, Scout> scoutLookup = new HashMap<Key<Scout>, Scout>();
		for(Scout s:getScoutManager().listScouts(eKey)) {
			scoutLookup.put(new Key<Scout>(Scout.class, s.getId()), s);
		}
		return scoutLookup;
	}

	private Map<Key<Clazz>, Clazz> getClazzLookup(Key<Event> eKey) {
		Map<Key<Clazz>, Clazz> staffLookup = new HashMap<Key<Clazz>, Clazz>();
		for(Clazz c:getEventManager().getClazzesByEvent(eKey)) {
			staffLookup.put(new Key<Clazz>(eKey, Clazz.class, c.getId()), c);
		}
		return staffLookup;
	}

	private EventManager getEventManager() {
		if (eventMgr == null) {
			eventMgr = new EventManager();
		}
		return eventMgr;
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
