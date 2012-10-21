package com.campscribe.controller.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campscribe.business.EventManager;
import com.campscribe.model.Event;
import com.campscribe.shared.EventDTO;
import com.googlecode.objectify.Key;

@Controller
public class EventServiceController {

//	@Autowired
	EventManager eventMgr = new EventManager();

	@RequestMapping(method=RequestMethod.POST, value = "/events/",headers="Accept=application/json")
	public @ResponseBody EventDTO addEvent(@RequestBody EventDTO eventDTO) {
		System.err.println("addEvent called");
		Event e = new Event(eventDTO.getDescription(), eventDTO.getStartDate(), eventDTO.getEndDate());
		Key<Event> eKey = eventMgr.addEvent(e);
		eventDTO.setId(eKey.getId());
		return eventDTO;
	}

	@RequestMapping(method=RequestMethod.DELETE, value = "/events/{id}",headers="Accept=application/json")
	public void deleteEvent(@PathVariable long id) {
		eventMgr.deleteEvent(id);
	}

	@RequestMapping(method=RequestMethod.GET, value = "/events/",headers="Accept=application/json")
	public @ResponseBody List<EventDTO> getAllEvents() {
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		List<Event> events = eventMgr.listEvents();
		for (Event e: events) {
			EventDTO eDTO = new EventDTO();
			eDTO.setDescription(e.getDescription());
			eDTO.setEndDate(e.getEndDate());
			eDTO.setStartDate(e.getStartDate());
			eDTO.setId(e.getId());
			eventDTOs.add(eDTO);
		}
		return eventDTOs;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/events/{id}",headers="Accept=application/json")
	public @ResponseBody EventDTO getEvent(@PathVariable long id) {
		Event e = eventMgr.getEvent(id);
		EventDTO dto = new EventDTO(e.getDescription(), e.getStartDate(), e.getEndDate());
		dto.setId(e.getId());
		return dto;
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/events/{id}",headers="Accept=application/json")
	public @ResponseBody EventDTO updateEvent(@PathVariable long id, @RequestBody EventDTO eventDTO) {
		Event e = new Event(eventDTO.getDescription(), eventDTO.getStartDate(), eventDTO.getEndDate());
		e.setId(id);
		eventMgr.updateEvent(e);
		eventDTO.setId(e.getId());
		return eventDTO;
	}

}
