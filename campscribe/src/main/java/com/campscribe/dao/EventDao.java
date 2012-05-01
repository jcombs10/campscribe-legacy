package com.campscribe.dao;

import java.util.ArrayList;
import java.util.List;

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

	//	public void addClazz(Long id, Clazz c) {
	//		EntityManager em = EMFService.get().createEntityManager();
	//
	//		em.getTransaction().begin();
	//	    try {
	//	    	Event e = em.find(Event.class, id);
	//	        if (e == null) {
	//	            throw new RuntimeException("Event " + id + " not found!");
	//	        }
	//	        e.getClazzes().add(c);
	//	        em.persist(e);
	//	        em.flush();
	//	        em.getTransaction().commit();
	//	    } finally {
	//	        if (em.getTransaction().isActive()) {
	//	            em.getTransaction().rollback();
	//	        }
	//			em.close();
	//	    }
	//	}
	//
}
