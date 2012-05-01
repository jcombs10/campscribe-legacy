package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.MeritBadgeDao;
import com.campscribe.model2.MeritBadge;
import com.googlecode.objectify.ObjectifyService;

public class MeritBadgeManager {
	
	private static boolean registered = false;

	public void addMeritBadge(MeritBadge mb) {
		if (!registered) {
			ObjectifyService.register(MeritBadge.class);
			registered = true;
		}

		MeritBadgeDao.INSTANCE.add(mb);
	}

	public void deleteMeritBadge(long id) {
		if (!registered) {
			ObjectifyService.register(MeritBadge.class);
			registered = true;
		}

		MeritBadgeDao.INSTANCE.remove(id);
	}

	public MeritBadge getMeritBadge(long id) {
		if (!registered) {
			ObjectifyService.register(MeritBadge.class);
			registered = true;
		}

		return MeritBadgeDao.INSTANCE.get(id);
	}

	public List<MeritBadge> listMeritBadges() {
		if (!registered) {
			ObjectifyService.register(MeritBadge.class);
			registered = true;
		}

		return MeritBadgeDao.INSTANCE.listMeritBadges();
	}

	public void updateMeritBadge(MeritBadge mb) {
		if (!registered) {
			ObjectifyService.register(MeritBadge.class);
			registered = true;
		}

		MeritBadgeDao.INSTANCE.update(mb);
	}

}
