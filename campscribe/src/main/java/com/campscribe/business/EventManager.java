package com.campscribe.business;

 import java.util.List;

import com.campscribe.dao.EventDao;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.campscribe.model2.Staff;
import com.googlecode.objectify.ObjectifyService;

public class EventManager {
	
	private static boolean registered = false;

	public void addEvent(Event e) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			registered = true;
		}

		EventDao.INSTANCE.add(e);
	}

	public void deleteEvent(long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			registered = true;
		}

		EventDao.INSTANCE.remove(id);
	}

	public Event getEvent(long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			registered = true;
		}

		return EventDao.INSTANCE.get(id);
	}

	public List<Event> listEvents() {
		if (!registered) {
			ObjectifyService.register(Event.class);
			registered = true;
		}

		return EventDao.INSTANCE.listEvents();
	}

	public void updateEvent(Event e) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			registered = true;
		}

		EventDao.INSTANCE.add(e);
	}

//	public void addClazz(Long id, Clazz c) {
//		EventDao.INSTANCE.addClazz(id, c);
//		
//	}
//
}
