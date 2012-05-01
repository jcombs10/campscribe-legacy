package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ClazzDao;
import com.campscribe.model2.Clazz;

public class ClazzManager {
	
	public void addClazz(Clazz c) {
		ClazzDao.INSTANCE.add(c);
	}

	public void deleteClazz(long id) {
		ClazzDao.INSTANCE.remove(id);
	}

	public Clazz getClazz(String id) {
		return ClazzDao.INSTANCE.get(id);
	}

	public List<Clazz> listClazzes() {
		return ClazzDao.INSTANCE.listClazzes();
	}

	public void addScoutsToClazz(String clazzId, List<Long> scoutList) {
		ClazzDao.INSTANCE.addScoutsToClazz(clazzId, scoutList);
	}

}
