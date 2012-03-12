package com.campscribe.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.campscribe.model.Clazz;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public enum ClazzDao {
	INSTANCE;

	public List<Clazz> listClazzes() {
		EntityManager em = EMFService.get().createEntityManager();

		//Read the existing entries
		Query q = em.createQuery("select c from Clazz c order by c.description");
		List<Clazz> clazzes = q.getResultList();
		return clazzes;
	}

	public void add(Clazz c) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(c);
			em.close();
		}
	}

	public Clazz get(String id) {
		EntityManager em = EMFService.get().createEntityManager();

		em.getTransaction().begin();
		try {
			Key k = KeyFactory.stringToKey(id);
			Clazz c = em.find(Clazz.class, k);
			//need to call size() to force lazy loading of the scoutIds list
			c.getEvent();
			c.getScoutIds().size();
	        em.getTransaction().commit();
			return c;
		} finally {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
			em.close();
		}
	}

	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Clazz c = em.find(Clazz.class, id);
			em.remove(c);
		} finally {
			em.close();
		}
	}

	public void addScoutsToClazz(String clazzId, List<Long> scoutList) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Key k = KeyFactory.stringToKey(clazzId);
			Clazz c = em.find(Clazz.class, k);
			//need to call size() to force lazy loading of the scoutIds list
			c.getEvent();
			c.getScoutIds().size();
			//weird - the list actually contains Integer references!
			Iterator iter = scoutList.iterator();
			while (iter.hasNext()) {
				Integer i = (Integer) iter.next();
				if (!c.getScoutIds().contains(i)) {
					c.getScoutIds().add(i.longValue());
				}
			}
		} finally {
			em.close();
		}
	}

}
