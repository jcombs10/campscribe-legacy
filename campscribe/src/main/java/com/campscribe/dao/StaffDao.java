package com.campscribe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.campscribe.model.Staff;

public enum StaffDao {
	INSTANCE;
	
	public List<Staff> listStaff() {
		EntityManager em = EMFService.get().createEntityManager();
		
		//Read the existing entries
		Query q = em.createQuery("select s from Staff s order by s.name desc");
		List<Staff> staffList = q.getResultList();
		return staffList;
	}
	
	public void add(Staff s) {
		synchronized(this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(s);
			em.close();
		}
	}

	public Staff get(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Staff e = em.find(Staff.class, id);
			e.getRoles();
			return e;
		} finally {
			em.close();
		}
	}

	public Staff getByUserName(String userName) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Query q = em.createQuery("select s from Staff s where s.userId = :userId");
			q.setParameter("userId", userName);
			Staff s = (Staff) q.getSingleResult();
			return s;
		} catch (NoResultException NRE) {
			return null;
		} finally {
			em.close();
		}
	}

	public void remove(long id) {
			EntityManager em = EMFService.get().createEntityManager();
			try {
				Staff s = em.find(Staff.class, id);
				em.remove(s);
			} finally {
				em.close();
			}
	}

}
