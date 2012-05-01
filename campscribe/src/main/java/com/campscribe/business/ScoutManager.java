package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ScoutDao;
import com.campscribe.model2.Scout;
import com.googlecode.objectify.ObjectifyService;

public class ScoutManager {
	
	private static boolean registered = false;

	public void addScout(Scout s) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		ScoutDao.INSTANCE.add(s);
	}

	public void deleteScout(long id) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		ScoutDao.INSTANCE.remove(id);
	}

	public Scout getScout(long id) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.get(id);
	}

	public List<Scout> listScouts() {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.listScouts(null, null, null);
	}

	public List<Scout> listScouts(String name, String unitType, String unitNumber) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.listScouts(name, unitType, unitNumber);
	}

	public void updateScout(Scout e) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		ScoutDao.INSTANCE.add(e);
	}

}
