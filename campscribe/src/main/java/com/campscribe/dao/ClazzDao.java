package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.campscribe.model.Clazz;

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

	public void remove(long id) {
			EntityManager em = EMFService.get().createEntityManager();
			try {
				Clazz c = em.find(Clazz.class, id);
				em.remove(c);
			} finally {
				em.close();
			}
	}

}
