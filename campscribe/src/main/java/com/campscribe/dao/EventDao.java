package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.campscribe.model.Event;

public enum EventDao {
	INSTANCE;
	
	public List<Event> listEvents() {
		EntityManager em = EMFService.get().createEntityManager();
		
		//Read the existing entries
		Query q = em.createQuery("select e from Event e order by e.startDate asc");
		List<Event> events = q.getResultList();
		return events;
	}
	
	public void add(Event e) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(e);
			em.close();
		}
	}

	public Event get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Event e = em.find(Event.class, id);
			return e;
		} finally {
			em.close();
		}
	}

	public void remove(long id) {
			EntityManager em = EMFService.get().createEntityManager();
			try {
				Event e = em.find(Event.class, id);
				em.remove(e);
			} finally {
				em.close();
			}
	}

}
