package com.campscribe.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campscribe.model.MeritBadge;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum MeritBadgeDao {
	INSTANCE;
	
	static {
		ObjectifyService.register(MeritBadge.class);
	}

	public void add(MeritBadge mb) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mb);
		}
	}

	public void delete(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<MeritBadge> mbKey = new Key<MeritBadge>(MeritBadge.class, id);
		ofy.delete(mbKey);
	}

	public MeritBadge get(long id) {
		Objectify ofy = ObjectifyService.begin();
		MeritBadge mb = ofy.get(MeritBadge.class, id);
		return mb;
	}

	public MeritBadge getByBadgeName(String badgeName) {
		Objectify ofy = ObjectifyService.begin();
		MeritBadge mb = ofy.query(MeritBadge.class).filter("badgeName", badgeName).get();
		return mb;
	}

	public Map<Key<MeritBadge>, MeritBadge> getLookup() {
		Objectify ofy = ObjectifyService.begin();
		Map<Key<MeritBadge>, MeritBadge> lookup = new HashMap<Key<MeritBadge>, MeritBadge>();
		for (MeritBadge mb:ofy.query(MeritBadge.class).list()) {
			lookup.put(new Key<MeritBadge>(MeritBadge.class, mb.getId()), mb);
		}
		return lookup;
	}

	public List<MeritBadge> listMeritBadges() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(MeritBadge.class).order("badgeName").list();
	}

	public void update(MeritBadge mb) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mb);
		}
	}

}
