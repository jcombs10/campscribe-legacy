package com.campscribe.business;

import java.util.List;
import java.util.Map;

import com.campscribe.dao.MeritBadgeDao;
import com.campscribe.model2.MeritBadge;
import com.googlecode.objectify.Key;

public class MeritBadgeManager {
	
	public void addMeritBadge(MeritBadge mb) {
		MeritBadgeDao.INSTANCE.add(mb);
	}

	public void deleteMeritBadge(long id) {
		MeritBadgeDao.INSTANCE.delete(id);
	}

	public MeritBadge getMeritBadge(long id) {
		return MeritBadgeDao.INSTANCE.get(id);
	}

	public MeritBadge getByBadgeName(String badgeName) {
		return MeritBadgeDao.INSTANCE.getByBadgeName(badgeName);
	}

	public Map<Key<MeritBadge>, MeritBadge> getLookup() {
		return MeritBadgeDao.INSTANCE.getLookup();
	}

	public List<MeritBadge> listMeritBadges() {
		return MeritBadgeDao.INSTANCE.listMeritBadges();
	}

	public void updateMeritBadge(MeritBadge mb) {
		MeritBadgeDao.INSTANCE.update(mb);
	}

}
