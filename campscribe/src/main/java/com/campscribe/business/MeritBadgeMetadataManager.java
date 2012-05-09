package com.campscribe.business;

import java.util.Map;

import com.campscribe.dao.MeritBadgeMetadataDao;
import com.campscribe.model2.MeritBadge;
import com.campscribe.model2.MeritBadgeMetadata;
import com.googlecode.objectify.Key;

public class MeritBadgeMetadataManager {
	
	public void addUpdate(MeritBadgeMetadata mb) {
		MeritBadgeMetadataDao.INSTANCE.add(mb);
	}

	public MeritBadgeMetadata getMetadataForBadge(Key<MeritBadge> mbKey) {
		return MeritBadgeMetadataDao.INSTANCE.getMetadataForBadge(mbKey);
	}

	public Map<Key<MeritBadge>, MeritBadgeMetadata> getAll() {
		return MeritBadgeMetadataDao.INSTANCE.getAll();
	}

}
