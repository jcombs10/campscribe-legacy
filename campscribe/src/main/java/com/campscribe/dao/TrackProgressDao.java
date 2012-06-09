package com.campscribe.dao;

import java.util.List;

import com.campscribe.model.Clazz;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum TrackProgressDao {
	INSTANCE;
	
	public void add(TrackProgress tp) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(tp);
		}
	}

	public TrackProgress get(Key<TrackProgress> k) {
		Objectify ofy = ObjectifyService.begin();
		TrackProgress s = ofy.get(k);
		return s;
	}

	public List<TrackProgress> getTrackingForClazz(Key<Clazz> key) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(TrackProgress.class).filter("clazzKey", key).list();
	}

	public List<TrackProgress> getTrackingForScout(Key<Scout> key) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(TrackProgress.class).filter("scoutKey", key).list();
	}

	public void remove(Key<TrackProgress> k) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(k);
	}

	public void update(TrackProgress s) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(s);
		}
	}

}
