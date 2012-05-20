package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.ClazzDao;
import com.campscribe.model.Clazz;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;



public class ClazzManager {

	public void addClazz(Clazz c) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.add(c);
	}

	public void deleteClazz(long id) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.remove(id);
	}

	public Clazz getClazz(Key<Clazz> clazzKey) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		return ClazzDao.INSTANCE.get(clazzKey);
	}

	public List<Clazz> listClazzes() {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		return ClazzDao.INSTANCE.listClazzes();
	}

	public void addScoutsToClazz(Key<Clazz> clazzKey, List<Key<Scout>> scoutList) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		ClazzDao.INSTANCE.addScoutsToClazz(clazzKey, scoutList);
	}

	public void updateComments(Long eventId, Long clazzId, String comments) {
		Key<Event> eKey = new Key<Event>(Event.class, eventId);
		Clazz c = ClazzDao.INSTANCE.get(new Key<Clazz>(eKey, Clazz.class, clazzId));
		c.setNotes(comments);
		ClazzDao.INSTANCE.update(c);
	}

}
