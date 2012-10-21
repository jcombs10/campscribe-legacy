package com.campscribe.business;

import java.util.List;
import java.util.Map;

import com.campscribe.dao.MeritBadgeMetadataDao;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.MeritBadgeMetadata;
import com.googlecode.objectify.Key;

public class MeritBadgeMetadataManager extends BaseManager {
	
	public void addUpdate(MeritBadgeMetadata mb) {
		MeritBadgeMetadataDao.INSTANCE.add(mb);
	}

	public MeritBadgeMetadata getMetadataForBadge(Key<MeritBadge> mbKey) {
		return MeritBadgeMetadataDao.INSTANCE.getMetadataForBadge(mbKey);
	}

	public Map<Key<MeritBadge>, MeritBadgeMetadata> getAll() {
		return MeritBadgeMetadataDao.INSTANCE.getAll();
	}

	public List<MeritBadgeMetadata> list() {
		return MeritBadgeMetadataDao.INSTANCE.list();
	}

	public MeritBadgeMetadata getMeritBadgeMetadata(Key<MeritBadgeMetadata> key) {
		return MeritBadgeMetadataDao.INSTANCE.get(key);
	}

	public void updateMeritBadgeMetadata(MeritBadgeMetadata mb) {
		MeritBadgeMetadataDao.INSTANCE.update(mb);
	}

}
