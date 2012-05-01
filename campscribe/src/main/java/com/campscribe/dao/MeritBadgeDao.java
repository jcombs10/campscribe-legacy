package com.campscribe.dao;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.model2.MeritBadge;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum MeritBadgeDao {
	INSTANCE;

	public void add(MeritBadge mb) {
		synchronized(this) {
//			EntityManager em = EMFService.get().createEntityManager();
//			em.persist(mb);
//			em.close();
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mb);
		}
	}

	public MeritBadge get(long id) {
//		EntityManager em = EMFService.get().createEntityManager();
//		try {
//			MeritBadge mb = em.find(MeritBadge.class, id);
//			return mb;
//		} finally {
//			em.close();
//		}
		Objectify ofy = ObjectifyService.begin();
		MeritBadge mb = ofy.get(MeritBadge.class, id);
		return mb;
	}

	public List<MeritBadge> listMeritBadges() {
//		EntityManager em = EMFService.get().createEntityManager();
//
//		//Read the existing entries
//		Query q = em.createQuery("select mb from MeritBadge mb order by mb.badgeName");
//		List<MeritBadge> todos = q.getResultList();
//		return todos;
		Objectify ofy = ObjectifyService.begin();
		Query<MeritBadge> q = ofy.query(MeritBadge.class);
		List<MeritBadge> allBadges = new ArrayList<MeritBadge>();
		for (MeritBadge mb: q) {
			allBadges.add(mb);
		}
		return allBadges;
	}

	public void remove(long id) {
//		EntityManager em = EMFService.get().createEntityManager();
//		try {
//			MeritBadge mb = em.find(MeritBadge.class, id);
//			em.remove(mb);
//		} finally {
//			em.close();
//		}
		Objectify ofy = ObjectifyService.begin();
		Key<MeritBadge> mbKey = new Key<MeritBadge>(MeritBadge.class, id);
		ofy.delete(mbKey);
	}

	public void update(MeritBadge mb) {
		synchronized(this) {
//			EntityManager em = EMFService.get().createEntityManager();
//			em.merge(mb);
//			em.close();
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mb);
		}
	}

}
