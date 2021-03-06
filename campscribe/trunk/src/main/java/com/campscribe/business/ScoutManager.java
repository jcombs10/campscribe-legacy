package com.campscribe.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.campscribe.dao.ScoutDao;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.Unit;
import com.campscribe.model.UnitComparator;
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

	public Set<Unit> listUnits(Key<Event> eventKey) {
		Set<Unit> units = new TreeSet<Unit>(new UnitComparator());
		List<Scout> scouts = ScoutDao.INSTANCE.listScouts(eventKey, null, null, null);
		for (Scout s:scouts) {
			units.add(new Unit(s.getUnitType(), s.getUnitNumber()));
		}
		return units;
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

	public List<Scout> getScoutsByEvent(Key<Event> eKey) {
		return ScoutDao.INSTANCE.getScoutsByEvent(eKey);
	}

	public List<Scout> getScoutsByUnit(Key<Event> key, String unitType,
			String unitNumber) {
		return listScouts(key, null, unitType, unitNumber);
	}

}
