package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.campscribe.model.Scout;

public enum ScoutDao {
	INSTANCE;
	
	public List<Scout> listScouts(String name, String unitType, String unitNumber) {
		EntityManager em = EMFService.get().createEntityManager();
		
		//Read the existing entries
		StringBuilder sb = new StringBuilder("select s from Scout s ");
		boolean whereAdded = false;
		if (StringUtils.isNotEmpty(name)) {
			if (!whereAdded) {
				sb.append("where ");
				whereAdded = true;
			} else {
				sb.append("and ");
			}
			sb.append("s.lastName >= :name and s.lastName < :nameMax ");
		}
		if (StringUtils.isNotEmpty(unitType)) {
			if (!whereAdded) {
				sb.append("where ");
				whereAdded = true;
			} else {
				sb.append("and ");
			}
			sb.append("s.unitType = :unitType ");
		}
		if (StringUtils.isNotEmpty(unitNumber)) {
			if (!whereAdded) {
				sb.append("where ");
				whereAdded = true;
			} else {
				sb.append("and ");
			}
			sb.append("s.unitNumber = :unitNumber ");
		}
		sb.append("order by s.lastName desc");
		Query q = em.createQuery(sb.toString());
		if (name != null) {
			q.setParameter("name", name);
			q.setParameter("nameMax", "u’"+name+"’ + u’\ufffd’");
		}
		if (unitType != null) {
			q.setParameter("unitType", unitType);
		}
		if (unitNumber != null) {
			q.setParameter("unitNumber", unitNumber);
		}
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
