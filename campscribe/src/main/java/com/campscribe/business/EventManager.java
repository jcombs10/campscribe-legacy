package com.campscribe.business;

 import java.util.List;

import com.campscribe.dao.EventDao;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class EventManager {
	
	private static boolean registered = false;

	public static boolean isRegistered() {
		return registered;
	}

	public static void setRegistered(boolean registered) {
		EventManager.registered = registered;
	}

	public Key<Event> addEvent(Event e) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.add(e);
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

	public Event getEvent(String eventName) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.get(eventName);
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

	public Key<Clazz> addClazz(Long id, Clazz c) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.addClazz(id, c);
	}

	public Clazz getClazz(Key<Event> e, String clazzDescription, Long mbId) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.getClazz(e, clazzDescription, mbId);
	}

	public void deleteClazz(Long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		EventDao.INSTANCE.deleteClazz(id);
	}

	public List<Clazz> getClazzes(List<Key<Clazz>> clazzKeys) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.getClazzes(clazzKeys);
	}

	public Event getEventForClazz(long id) {
		if (!registered) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			registered = true;
		}

		return EventDao.INSTANCE.getEventForClazz(id);
	}

}
