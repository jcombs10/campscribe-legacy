package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ScoutDao;
import com.campscribe.model.Scout;

public class ScoutManager {
	
	public void addScout(Scout s) {
		ScoutDao.INSTANCE.add(s);
	}

	public void deleteScout(long id) {
		ScoutDao.INSTANCE.remove(id);
	}

	public Scout getScout(long id) {
		return ScoutDao.INSTANCE.get(id);
	}

	public List<Scout> listScouts() {
		return ScoutDao.INSTANCE.listScouts(null, null, null);
	}

	public List<Scout> listScouts(String name, String unitType, String unitNumber) {
		return ScoutDao.INSTANCE.listScouts(name, unitType, unitNumber);
	}

	public void updateScout(Scout e) {
		ScoutDao.INSTANCE.add(e);
	}

}
