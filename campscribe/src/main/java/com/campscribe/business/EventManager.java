package com.campscribe.business;

 import java.util.List;

import com.campscribe.dao.EventDao;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class EventManager {
	
	private static boolean registered = false;

	public void addEvent(Event e) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		EventDao.INSTANCE.add(e);
	}

	public void deleteEvent(long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		EventDao.INSTANCE.remove(id);
	}

	public Event getEvent(long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.get(id);
	}

	public List<Event> listEvents() {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.listEvents();
	}

	public void updateEvent(Event e) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		EventDao.INSTANCE.add(e);
	}

	public void addClazz(Long id, Clazz c) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		EventDao.INSTANCE.addClazz(id, c);
	}

	public List<Clazz> getClazzes(List<Key<Clazz>> clazzKeys) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.getClazzes(clazzKeys);
	}

}
