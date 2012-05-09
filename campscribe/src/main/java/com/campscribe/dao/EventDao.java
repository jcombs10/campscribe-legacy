package com.campscribe.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
		Collections.sort(allClazzes, new Comparator<Clazz>() {

			@Override
			public int compare(Clazz o1, Clazz o2) {
				if (o1.getDescription().equals(o2.getDescription())) {
					return o1.getMbName().compareTo(o2.getMbName());
				}
				Date d1 = new Date();
				Date d2 = new Date(d1.getTime());
				String hours1 = o1.getDescription().split(":")[0];
				String hours2 = o2.getDescription().split(":")[0];
				int hoursInt1 = Integer.parseInt(hours1);
				if (hoursInt1 < 9) {
					hoursInt1 += 12;
				}
				int hoursInt2 = Integer.parseInt(hours2);
				if (hoursInt2 < 9) {
					hoursInt2 += 12;
				}
				d1.setHours(hoursInt1);
				d2.setHours(hoursInt2);
				return d1.compareTo(d2);
			}
			
		});
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

}
