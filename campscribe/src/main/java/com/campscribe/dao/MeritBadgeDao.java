package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.campscribe.model.MeritBadge;

public enum MeritBadgeDao {
	INSTANCE;

	public void add(MeritBadge mb) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(mb);
			em.close();
		}
	}

	public MeritBadge get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			MeritBadge mb = em.find(MeritBadge.class, id);
			return mb;
		} finally {
			em.close();
		}
	}

	public List<MeritBadge> listMeritBadges() {
		EntityManager em = EMFService.get().createEntityManager();

		//Read the existing entries
		Query q = em.createQuery("select mb from MeritBadge mb order by mb.badgeName");
		List<MeritBadge> todos = q.getResultList();
		return todos;
	}

	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			MeritBadge mb = em.find(MeritBadge.class, id);
			em.remove(mb);
		} finally {
			em.close();
		}
	}

	public void update(MeritBadge mb) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.merge(mb);
			em.close();
		}
	}

}
