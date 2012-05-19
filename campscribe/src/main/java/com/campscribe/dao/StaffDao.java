package com.campscribe.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.campscribe.model.MeritBadge;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum StaffDao {
	INSTANCE;
	
	static {
		ObjectifyService.register(Staff.class);
	}
	
	public void add(Staff s) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(s);
		}
	}

	public Staff get(long id) {
		Objectify ofy = ObjectifyService.begin();
		Staff s = ofy.get(Staff.class, id);
		return s;
	}

	public Map<Key<Staff>, Staff> getLookup() {
		Objectify ofy = ObjectifyService.begin();
		Map<Key<Staff>, Staff> lookup = new HashMap<Key<Staff>, Staff>();
		for (Staff s:ofy.query(Staff.class).list()) {
			lookup.put(new Key<Staff>(Staff.class, s.getId()), s);
		}
		return lookup;
	}

	public Staff getByUserName(String userName) {
		Objectify ofy = ObjectifyService.begin();
		Staff s = ofy.query(Staff.class).filter("userId", userName).get();
		return s;
	}

	public List<Staff> listStaff() {
		Objectify ofy = ObjectifyService.begin();
		Query<Staff> q = ofy.query(Staff.class).order("userId");
		List<Staff> allStaff = new ArrayList<Staff>();
		for (Staff s: q) {
			allStaff.add(s);
		}
		return allStaff;
	}
	
	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Staff> s = new Key<Staff>(Staff.class, id);
		ofy.delete(s);
	}

	public void update(Staff s) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(s);
		}
	}

}
