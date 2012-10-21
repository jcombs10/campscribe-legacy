package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.CampInfoDao;
import com.campscribe.model.CampInfo;
import com.googlecode.objectify.Key;

public class CampInfoManager extends BaseManager {
	
	public Key<CampInfo> addCampInfo(CampInfo ci) {
		return CampInfoDao.INSTANCE.add(ci);
	}

	public void deleteCampInfo(long id) {
		CampInfoDao.INSTANCE.remove(id);
	}

	public CampInfo getCampInfo(long id) {
		return CampInfoDao.INSTANCE.get(new Key<CampInfo>(CampInfo.class, id));
	}

	public List<CampInfo> listCampInfos() {
		return CampInfoDao.INSTANCE.listCampInfos();
	}

	public void updateCampInfo(CampInfo ci) {
		CampInfoDao.INSTANCE.update(ci);
	}

}
