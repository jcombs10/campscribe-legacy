package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ClazzDao;
import com.campscribe.model.Clazz;

public class ClazzManager {
	
	public void addClazz(Clazz c) {
		ClazzDao.INSTANCE.add(c);
	}

	public void deleteClazz(long id) {
		ClazzDao.INSTANCE.remove(id);
	}

	public List<Clazz> listClazzes() {
		return ClazzDao.INSTANCE.listClazzes();
	}

}
