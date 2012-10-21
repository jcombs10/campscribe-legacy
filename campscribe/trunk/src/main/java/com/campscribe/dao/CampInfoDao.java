package com.campscribe.dao;

import java.util.Collections;
import java.util.List;

import com.campscribe.model.CampInfo;
import com.campscribe.model.CampInfoComparator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum CampInfoDao {
	INSTANCE;

	public Key<CampInfo> add(CampInfo ci) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			return ofy.put(ci);
		}
	}

	public CampInfo get(Key<CampInfo> ciKey) {
		Objectify ofy = ObjectifyService.begin();
		CampInfo ci = ofy.get(ciKey);
		return ci;
	}

	public List<CampInfo> listCampInfos() {
		Objectify ofy = ObjectifyService.begin();
		List<CampInfo> allCampInfos = ofy.query(CampInfo.class).list();
		Collections.sort(allCampInfos, new CampInfoComparator());
		return allCampInfos;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<CampInfo> ci = new Key<CampInfo>(CampInfo.class, id);
		ofy.delete(ci);
	}

	public void update(CampInfo ci) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(ci);
		}
	}

}