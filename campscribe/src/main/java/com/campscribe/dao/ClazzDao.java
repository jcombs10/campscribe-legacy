package com.campscribe.dao;

 import java.util.Collections;
import java.util.List;

import com.campscribe.model2.Clazz;
import com.campscribe.model2.ClazzComparator;
import com.campscribe.model2.Scout;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum ClazzDao {
	INSTANCE;

	public void add(Clazz c) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(c);
		}
	}

	public Clazz get(Key<Clazz> clazzKey) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = ofy.get(clazzKey);
		return c;
	}

	public List<Clazz> listClazzes() {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Clazz> e = new Key<Clazz>(Clazz.class, id);
		ofy.delete(e);
	}

	public void addScoutsToClazz(Key<Clazz> clazzKey, List<Key<Scout>> scoutList) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = get(clazzKey);

		if (c == null) {
			throw new RuntimeException("Clazz " + clazzKey.getId() + " not found!");
		}
		
		for (Key<Scout> scoutId:scoutList) {
			if (!c.getScoutIds().contains(scoutId)) {
				c.getScoutIds().add(scoutId);
			}
		}
		
		ofy.put(c);
	}

}
