package com.campscribe.business;

 import java.util.List;

import com.campscribe.dao.EventDao;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

public class EventManager extends BaseManager {
	
	public Key<Event> addEvent(Event e) {
		return EventDao.INSTANCE.add(e);
	}

	public void deleteEvent(long id) {
		EventDao.INSTANCE.remove(new Key<Event>(Event.class, id));
	}

	public Event getEvent(long id) {
		return EventDao.INSTANCE.get(new Key<Event>(Event.class, id));
	}

	public Event getEvent(String eventName) {
		return EventDao.INSTANCE.get(eventName);
	}

	public List<Event> listEvents() {
		return EventDao.INSTANCE.listEvents();
	}

	public void updateEvent(Event e) {
		EventDao.INSTANCE.add(e);
	}

	public Key<Clazz> addClazz(Long id, Clazz c) {
		return EventDao.INSTANCE.addClazz(new Key<Event>(Event.class, id), c);
	}

	public Clazz getClazz(Key<Event> e, String clazzDescription, Key<MeritBadge> mbId) {
		return EventDao.INSTANCE.getClazz(e, clazzDescription, mbId);
	}

	public void deleteClazz(Long id) {
		EventDao.INSTANCE.deleteClazz(id);
	}

	public List<Clazz> getClazzes(List<Key<Clazz>> clazzKeys) {
		return EventDao.INSTANCE.getClazzes(clazzKeys);
	}

	public Event getEventForClazz(long id) {
		return EventDao.INSTANCE.getEventForClazz(id);
	}

	public List<Clazz> getClazzesByCounselor(Key<Staff> key) {
		return EventDao.INSTANCE.getClazzesByCounselor(key);
	}

	public List<Clazz> getClazzesByCounselor(Key<Event> eventKey, Key<Staff> key) {
		return EventDao.INSTANCE.getClazzesByCounselor(eventKey, key);
	}

	public List<Clazz> getClazzesByEvent(Key<Event> eKey) {
		return EventDao.INSTANCE.getClazzesByEvent(eKey);
	}

	public List<Clazz> getClazzesByProgramArea(String programArea) {
		return EventDao.INSTANCE.getClazzesByProgramArea(programArea);
	}

	public List<Clazz> getClazzesByProgramArea(Key<Event> eventKey, String programArea) {
		return EventDao.INSTANCE.getClazzesByProgramArea(eventKey, programArea);
	}

}
