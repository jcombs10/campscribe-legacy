package com.campscribe.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.campscribe.business.EventManager;
import com.campscribe.business.ScoutManager;
import com.campscribe.model.Event;
import com.campscribe.model.EventUtil;
import com.googlecode.objectify.Key;

@Controller
public class ScoutController {

	protected final Log logger = LogFactory.getLog(getClass());
	private ScoutManager mgr;
	private EventManager eventMgr;

	@RequestMapping(value="/scouts.cs", method=RequestMethod.POST)
	public ModelAndView listScouts(@ModelAttribute("eventFilter")
	EventFilterFBO fbo, BindingResult result)
			throws ServletException, IOException {
		
		return listScouts(fbo);
	}

	@RequestMapping(value="/scouts.cs", method=RequestMethod.GET)
	public ModelAndView listScouts()
			throws ServletException, IOException {
		
		List<Event> events = getEventManager().listEvents();

		EventFilterFBO fbo = new EventFilterFBO();
		fbo.setEventId(EventUtil.findCurrentEventId(events));

		return listScouts(fbo);
	}

	private ModelAndView listScouts(EventFilterFBO fbo)
			throws ServletException, IOException {

		logger.info("Returning scouts view");

		ModelAndView mav = new ModelAndView("scouts.jsp");
		List<Event> events = getEventManager().listEvents();
		mav.addObject("eventList", events);
		mav.addObject("eventFilter", fbo);
		mav.addObject("scouts", getScoutManager().listScouts(new Key<Event>(Event.class, fbo.getEventId())));

		return mav;
	}

	private EventManager getEventManager() {
		if (eventMgr == null) {
			eventMgr = new EventManager();
		}
		return eventMgr;
	}

	private ScoutManager getScoutManager() {
		if (mgr == null) {
			mgr = new ScoutManager();
		}
		return mgr;
	}

}
