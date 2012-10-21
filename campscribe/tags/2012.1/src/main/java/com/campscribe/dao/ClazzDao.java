package com.campscribe.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.campscribe.model.Clazz;
import com.campscribe.model.ClazzComparator;
import com.campscribe.model.Event;
import com.campscribe.model.MeritBadge;
import com.campscribe.model.Requirement;
import com.campscribe.model.Scout;
import com.campscribe.model.TrackProgress;
import com.campscribe.model.TrackProgress.DateAttendance;
import com.campscribe.model.TrackProgress.RequirementCompletion;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public enum ClazzDao {
	INSTANCE;

	private final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

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

	public List<Clazz> listClazzes(Key<Event> eventKey) {
		Objectify ofy = ObjectifyService.begin();
		List<Clazz> allClazzes = ofy.query(Clazz.class).ancestor(eventKey).list();
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
			MeritBadge mb = ofy.get(c.getMbId());
			tp.setRequirementList(buildRequirementList(mb.getRequirements(), "", 0));
			ofy.put(tp);
		}

		ofy.put(c);
	}

	private List<RequirementCompletion> buildRequirementList(
			List<Requirement> requirements, String parentReqStr, int level) {
		List<RequirementCompletion> requirementList = new ArrayList<RequirementCompletion>();
		int i = 1;
		for (Requirement req:requirements) {
			RequirementCompletion rc = new RequirementCompletion();
			String reqStr = "";
			if (level == 0) {
				reqStr = i+"";
			} else if (level == 1) {
				reqStr = parentReqStr+"."+chars[i-1];
			} else if (level == 2) {
				reqStr = parentReqStr+"."+i;
			}
			rc.setReqNumber(reqStr);
			rc.setCompleted(false);
			if (req.getSubRequirements().size() == 0) {
				requirementList.add(rc);
			} else {
				requirementList.addAll(buildRequirementList(req.getSubRequirements(), reqStr, level+1));
			}
			i++;
		}
		return requirementList;
	}

	public void update(Clazz c) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(c);
		}
	}

	public void deleteScoutFromClazz(Key<Clazz> clazzKey, Key<Scout> scoutKey) {
		Objectify ofy = ObjectifyService.begin();
		Clazz c = get(clazzKey);

		if (c == null) {
			throw new RuntimeException("Clazz " + clazzKey.getId() + " not found!");
		}

		c.getScoutIds().remove(scoutKey);
		ofy.put(c);

		List<TrackProgress> tpList = ofy.query(TrackProgress.class).filter("clazzKey", clazzKey).list();
		for (TrackProgress tp: tpList) {
			if (tp.getScoutKey().equals(scoutKey)) {
				ofy.delete(tp);
			}
		}
	}

}