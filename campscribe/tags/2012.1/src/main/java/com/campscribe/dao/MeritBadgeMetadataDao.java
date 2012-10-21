package com.campscribe.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campscribe.model.MeritBadge;
import com.campscribe.model.MeritBadgeMetadata;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum MeritBadgeMetadataDao {
	INSTANCE;

	public void add(MeritBadgeMetadata mbMd) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mbMd);
		}
	}

	public MeritBadgeMetadata getMetadataForBadge(Key<MeritBadge> mbKey) {
		Objectify ofy = ObjectifyService.begin();
		return (MeritBadgeMetadata) ofy.query(MeritBadgeMetadata.class).filter("mbKey", mbKey).get();		
	}

	public Map<Key<MeritBadge>, MeritBadgeMetadata> getAll() {
		Objectify ofy = ObjectifyService.begin();
		Query<MeritBadgeMetadata> m = ofy.query(MeritBadgeMetadata.class);
		
		Map<Key<MeritBadge>, MeritBadgeMetadata> resultMap = new HashMap<Key<MeritBadge>, MeritBadgeMetadata>();
		for (MeritBadgeMetadata mbMd:m.fetch()) {
			resultMap.put(mbMd.getMbKey(), mbMd);
		}
		return resultMap;
	}

	public List<MeritBadgeMetadata> list() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(MeritBadgeMetadata.class).list();
	}

	public MeritBadgeMetadata get(Key<MeritBadgeMetadata> key) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(key);
	}

	public void update(MeritBadgeMetadata mbMd) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(mbMd);
		}
	}

}
