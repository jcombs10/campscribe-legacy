package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.campscribe.model.Event;
import com.campscribe.model.Scout;

public enum ScoutDao {
	INSTANCE;
	
	public List<Scout> listScouts() {
		EntityManager em = EMFService.get().createEntityManager();
		
		//Read the existing entries
		Query q = em.createQuery("select s from Scout s order by s.lastName desc");
		List<Scout> scouts = q.getResultList();
		return scouts;
	}
	
	public void add(Scout s) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(s);
			em.close();
		}
	}

	public Scout get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Scout e = em.find(Scout.class, id);
			return e;
		} finally {
			em.close();
		}
	}

	public void remove(long id) {
			EntityManager em = EMFService.get().createEntityManager();
			try {
				Scout s = em.find(Scout.class, id);
				em.remove(s);
			} finally {
				em.close();
			}
	}

}
