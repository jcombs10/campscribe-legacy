package com.campscribe.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum EventDao {
	INSTANCE;

	public void add(Event e) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(e);
		}
	}

	public Event get(long id) {
		Objectify ofy = ObjectifyService.begin();
		Event e = ofy.get(Event.class, id);
		return e;
	}

	public List<Event> listEvents() {
		//TODO - sorting
		Objectify ofy = ObjectifyService.begin();
		Query<Event> q = ofy.query(Event.class);
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

	public void addClazz(Long id, Clazz c) {
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
		
	}

	public List<Clazz> getClazzes(List<Key<Clazz>> clazzKeys) {
		//TODO - sorting
		Objectify ofy = ObjectifyService.begin();
		Map<Key<Clazz>, Clazz> clazzMap = ofy.get(clazzKeys);
		List<Clazz> allClazzes = new ArrayList<Clazz>();
		for (Clazz c: clazzMap.values()) {
			allClazzes.add(c);
		}
		return allClazzes;
	}

}
