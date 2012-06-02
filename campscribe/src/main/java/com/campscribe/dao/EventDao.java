package com.campscribe.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.campscribe.model.Clazz;
import com.campscribe.model.ClazzComparator;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Scout;
import com.campscribe.model.Staff;
import com.campscribe.model.TrackProgress;
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

	public Event get(Key<Event> eKey) {
		Objectify ofy = ObjectifyService.begin();
		Event e = ofy.get(eKey);
		return e;
	}

	public Event get(String eventName) {
		Objectify ofy = ObjectifyService.begin();
		Event e = ofy.query(Event.class).filter("description", eventName).get();
		return e;
	}

	public Clazz getClazz(long id) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = ofy.get(new Key<Clazz>(Clazz.class, id));
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

	public void remove(Key<Event> eKey) {
		Objectify ofy = ObjectifyService.begin();
		Event e = get(eKey);
		deleteClazzes(e.getClazzes());
		findAndDeleteScouts(eKey);
		ofy.delete(eKey);
	}

	private void deleteClazzes(List<Key<Clazz>> clazzes) {
		Objectify ofy = ObjectifyService.begin();
		for (Key<Clazz> clazzKey:clazzes) {
			deleteTracking(clazzKey);
		}
		ofy.delete(clazzes);
	}

	private void deleteTracking(Key<Clazz> clazzKey) {
		Objectify ofy = ObjectifyService.begin();
		List<Key<TrackProgress>> tpKeys = ofy.query(TrackProgress.class).filter("clazzKey", clazzKey).listKeys();
		ofy.delete(tpKeys);
	}

	private void findAndDeleteScouts(Key<Event> eKey) {
		Objectify ofy = ObjectifyService.begin();
		List<Key<Scout>> scoutKeys = ofy.query(Scout.class).filter("eventKey", eKey).listKeys();
		ofy.delete(scoutKeys);
	}

	public Key<Clazz> addClazz(Key<Event> eKey, Clazz c) {
		Objectify ofy = ObjectifyService.begin();
		Event e = get(eKey);

		if (e == null) {
			throw new RuntimeException("Event " + eKey.getId() + " not found!");
		}

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

	public Clazz getClazz(Key<Event> e, String clazzDescription, Key<MeritBadge> mbId) {
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

	public List<Clazz> getClazzesByCounselor(Key<Event> eventKey, Key<Staff> staffKey) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).filter("staffId", staffKey).ancestor(eventKey).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public List<Clazz> getClazzesByProgramArea(String programArea) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).filter("programArea", programArea).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public List<Clazz> getClazzesByProgramArea(Key<Event> eventKey, String programArea) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).filter("programArea", programArea).ancestor(eventKey).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

}
