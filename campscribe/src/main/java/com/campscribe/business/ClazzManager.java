package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ClazzDao;
import com.campscribe.model2.Clazz;
import com.campscribe.model2.Event;
import com.campscribe.model2.Scout;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ClazzManager {
	
	public void addClazz(Clazz c) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.add(c);
	}

	public void deleteClazz(long id) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.remove(id);
	}

	public Clazz getClazz(Key<Clazz> clazzKey) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			EventManager.setRegistered(true);
		}

		return ClazzDao.INSTANCE.get(clazzKey);
	}

	public List<Clazz> listClazzes() {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			EventManager.setRegistered(true);
		}

		return ClazzDao.INSTANCE.listClazzes();
	}

	public void addScoutsToClazz(Key<Clazz> clazzKey, List<Key<Scout>> scoutList) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.addScoutsToClazz(clazzKey, scoutList);
	}

}
