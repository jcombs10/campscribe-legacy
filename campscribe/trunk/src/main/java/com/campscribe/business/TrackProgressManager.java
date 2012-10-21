package com.campscribe.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.campscribe.dao.TrackProgressDao;
import com.campscribe.model.Clazz;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.googlecode.objectify.Key;

public class TrackProgressManager extends BaseManager {
	
	public void add(Key<Scout> scoutKey, Date startDate, Date endDate) {
		TrackProgress tp = new TrackProgress();
		tp.setScoutKey(scoutKey);
		Date curDate = (Date) startDate.clone();
		List<DateAttendance> attendanceList = new ArrayList<DateAttendance>();
		while (!curDate.after(endDate)) {
			DateAttendance da = new DateAttendance();
			da.setDate((Date) curDate.clone());
			da.setPresent(false);
			curDate.setDate(curDate.getDate()+1);
			attendanceList.add(da);
		}
		tp.setAttendanceList(attendanceList);
		TrackProgressDao.INSTANCE.add(tp);
	}

	public List<TrackProgress> getTrackingForClazz(Key<Clazz> key) {
		return TrackProgressDao.INSTANCE.getTrackingForClazz(key);
	}

	public List<TrackProgress> getTrackingForScout(Key<Scout> key) {
		return TrackProgressDao.INSTANCE.getTrackingForScout(key);
	}

	public TrackProgress get(Key<TrackProgress> key) {
		return TrackProgressDao.INSTANCE.get(key);
	}

	public void update(TrackProgress tracker) {
		TrackProgressDao.INSTANCE.update(tracker);
	}

}
