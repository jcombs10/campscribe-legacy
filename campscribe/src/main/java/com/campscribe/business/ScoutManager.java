package com.campscribe.business;

import java.util.List;
import java.util.Map;

import com.campscribe.dao.ScoutDao;
import com.campscribe.model.Scout;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ScoutManager {
	
	private static boolean registered = false;

	public Key<Scout> addScout(Scout s) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.add(s);
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

	public Scout getScout(String firstName, String lastName, String unitType, String unitNumber) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.get(firstName, lastName, unitType, unitNumber);
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

	public Map<Key<Scout>, Scout> getScouts(List<Key<Scout>> scoutIds) {
		if (!registered) {
			ObjectifyService.register(Scout.class);
			registered = true;
		}

		return ScoutDao.INSTANCE.getScouts(scoutIds);
	}

}
