package com.campscribe.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.campscribe.client.Clazz;
import com.campscribe.dao.TrackProgressDao;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class TrackProgressManager {
	
	public void add(Key<Scout> scoutKey, Date startDate, Date endDate) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

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
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		return TrackProgressDao.INSTANCE.getTrackingForClazz(key);
	}

	public TrackProgress get(Key<TrackProgress> key) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		return TrackProgressDao.INSTANCE.get(key);
	}

	public void update(TrackProgress tracker) {
		if (!EventManager.isRegistered()) {
			ObjectifyService.register(Event.class);
			ObjectifyService.register(Clazz.class);
			ObjectifyService.register(TrackProgress.class);
			EventManager.setRegistered(true);
		}

		TrackProgressDao.INSTANCE.update(tracker);
	}

}
