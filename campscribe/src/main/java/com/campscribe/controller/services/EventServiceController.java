package com.campscribe.controller.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.EventManager;
import com.campscribe.model.Event;
import com.campscribe.shared.EventDTO;

@Controller
public class EventServiceController {

//	@Autowired
	EventManager eventMgr = new EventManager();

	@RequestMapping(method=RequestMethod.GET, value = "/events/",headers="Accept=application/json")
	public @ResponseBody List<Event> getAllEvents() {
		List<Event> events = eventMgr.listEvents();
		return events;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/events/",headers="Accept=application/json")
	public @ResponseBody Event addEvent(@RequestBody EventDTO eventDTO) {
		System.err.println("addEvent called");
		Event e = new Event(eventDTO.getDescription(), eventDTO.getStartDate(), eventDTO.getEndDate());
		eventMgr.addEvent(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/events/{id}",headers="Accept=application/json")
	public @ResponseBody Event getEvent(@PathVariable long id) {
		return eventMgr.getEvent(id);
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/events/{id}",headers="Accept=application/json")
	public @ResponseBody Event updateEvent(@PathVariable long id, @RequestBody Event e) {
		eventMgr.updateEvent(e);
		return e;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/events/{id}",headers="Accept=application/json")
	public @ResponseBody void deleteEvent(@PathVariable long id) {
		eventMgr.deleteEvent(id);
	}

}
