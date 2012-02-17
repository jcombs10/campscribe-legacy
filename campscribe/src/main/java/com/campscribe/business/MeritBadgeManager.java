package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.MeritBadgeDao;
import com.campscribe.model.MeritBadge;

public class MeritBadgeManager {
	
	public void addMeritBadge(MeritBadge mb) {
		MeritBadgeDao.INSTANCE.add(mb);
	}

	public void deleteMeritBadge(long id) {
		MeritBadgeDao.INSTANCE.remove(id);
	}

	public MeritBadge getMeritBadge(long id) {
		return MeritBadgeDao.INSTANCE.get(id);
	}

	public List<MeritBadge> listMeritBadges() {
		return MeritBadgeDao.INSTANCE.listMeritBadges();
	}

	public void updateMeritBadge(MeritBadge mb) {
		MeritBadgeDao.INSTANCE.add(mb);
	}

}
