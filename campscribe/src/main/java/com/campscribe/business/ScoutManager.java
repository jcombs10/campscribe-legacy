package com.campscribe.business;

import java.util.List;
import java.util.Map;

import com.campscribe.dao.ScoutDao;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.googlecode.objectify.Key;

public class ScoutManager extends BaseManager {
	
	public Key<Scout> addScout(Scout s) {
		return ScoutDao.INSTANCE.add(s);
	}

	public void deleteScout(long id) {
		ScoutDao.INSTANCE.remove(id);
	}

	public Scout getScout(long id) {
		return ScoutDao.INSTANCE.get(id);
	}

	public Scout getScout(String firstName, String lastName, String unitType, String unitNumber) {
		return ScoutDao.INSTANCE.get(firstName, lastName, unitType, unitNumber);
	}

	public List<Scout> listScouts(Key<Event> eKey) {
		return ScoutDao.INSTANCE.listScouts(eKey, null, null, null);
	}

	public List<Scout> listScouts(Key<Event> eKey, String name, String unitType, String unitNumber) {
		return ScoutDao.INSTANCE.listScouts(eKey, name, unitType, unitNumber);
	}

	public void updateScout(Scout e) {
		ScoutDao.INSTANCE.add(e);
	}

	public Map<Key<Scout>, Scout> getScouts(List<Key<Scout>> scoutIds) {
		return ScoutDao.INSTANCE.getScouts(scoutIds);
	}

}
