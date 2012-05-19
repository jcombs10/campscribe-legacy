package com.campscribe.dao;

 import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.campscribe.model.Clazz;
import com.campscribe.model.ClazzComparator;
import com.campscribe.model.Event;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum ClazzDao {
	INSTANCE;

	public void add(Clazz c) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(c);
		}
	}

	public Clazz get(Key<Clazz> clazzKey) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = ofy.get(clazzKey);
		return c;
	}

	public List<Clazz> listClazzes() {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).list();
		Collections.sort(allClazzes, new ClazzComparator());
		return allClazzes;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Clazz> e = new Key<Clazz>(Clazz.class, id);
		ofy.delete(e);
	}

	public void addScoutsToClazz(Key<Clazz> clazzKey, List<Key<Scout>> scoutList) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = get(clazzKey);

		if (c == null) {
			throw new RuntimeException("Clazz " + clazzKey.getId() + " not found!");
		}
		
		Event e = ofy.get(c.getEvent());
		for (Key<Scout> scoutId:scoutList) {
			if (!c.getScoutIds().contains(scoutId)) {
				c.getScoutIds().add(scoutId);
			}
		}
		for (Key<Scout> s:scoutList) {
			TrackProgress tp = new TrackProgress();
			tp.setScoutKey(s);
			tp.setClazzKey(clazzKey);
			Date curDate = (Date) e.getStartDate().clone();
			List<DateAttendance> attendanceList = new ArrayList<DateAttendance>();
			while (!curDate.after(e.getEndDate())) {
				DateAttendance da = new DateAttendance();
				da.setDate((Date) curDate.clone());
				da.setPresent(false);
				curDate.setDate(curDate.getDate()+1);
				attendanceList.add(da);
			}
			tp.setAttendanceList(attendanceList);
			ofy.put(tp);
		}
		
		ofy.put(c);
	}

}
