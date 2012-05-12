package com.campscribe.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.campscribe.model2.Clazz;
import com.campscribe.model2.ClazzComparator;
import com.campscribe.model2.Event;
import com.campscribe.model2.Staff;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum EventDao {
	INSTANCE;

	public Key<Event> add(Event e) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			return ofy.put(e);
		}
	}

	public Event get(long id) {
		Objectify ofy = ObjectifyService.begin();
		Event e = ofy.get(Event.class, id);
		return e;
	}

	public Event get(String eventName) {
		Objectify ofy = ObjectifyService.begin();
		Event e = ofy.query(Event.class).filter("description", eventName).get();
		return e;
	}

	public Clazz getClazz(long id) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = ofy.get(Clazz.class, id);
		return c;
	}

	public List<Event> listEvents() {
		Objectify ofy = ObjectifyService.begin();
		Query<Event> q = ofy.query(Event.class).order("startDate");
		List<Event> allEvents = new ArrayList<Event>();
		for (Event e: q) {
			allEvents.add(e);
		}
		return allEvents;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Event> e = new Key<Event>(Event.class, id);
		ofy.delete(e);
	}

	public Key<Clazz> addClazz(Long id, Clazz c) {
		Objectify ofy = ObjectifyService.begin();
		Event e = get(id);

		if (e == null) {
			throw new RuntimeException("Event " + id + " not found!");
		}

		Key<Event> eKey = new Key<Event>(Event.class, id);
		c.setEvent(eKey);
		Key<Clazz> cKey = ofy.put(c);

		e.getClazzes().add(cKey);
		ofy.put(e);
		return cKey;
	}

	public void deleteClazz(Long id) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = getClazz(id);

		if (c == null) {
			throw new RuntimeException("Clazz " + id + " not found!");
		}

		Key<Event> eKey = c.getEvent();
		Event e = ofy.get(eKey);
		e.getClazzes().remove(new Key<Clazz>(Clazz.class, id));
		ofy.put(e);

		ofy.delete(c);

	}

	public Clazz getClazz(Key<Event> e, String clazzDescription, Long mbId) {
		Objectify ofy = ObjectifyService.begin();

		Clazz c = ofy.query(Clazz.class).filter("description", clazzDescription).filter("mbId", mbId).ancestor(e).get();

		return c;
	}

	public List<Clazz> getClazzes(List<Key<Clazz>> clazzKeys) {
		Objectify ofy = ObjectifyService.begin();
		Map<Key<Clazz>, Clazz> clazzMap = ofy.get(clazzKeys);
		List<Clazz> allClazzes = new ArrayList<Clazz>();
		for (Clazz c: clazzMap.values()) {
			allClazzes.add(c);
		}
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public Event getEventForClazz(long id) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = getClazz(id);

		if (c == null) {
			throw new RuntimeException("Clazz " + id + " not found!");
		}

		Key<Event> eKey = c.getEvent();
		Event e = ofy.get(eKey);
		return e;
	}

	public List<Clazz> getClazzesByCounselor(Key<Staff> staffKey) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).filter("staffId", staffKey).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public List<Clazz> getClazzesByProgramArea(String programArea) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).filter("programArea", programArea).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

}
