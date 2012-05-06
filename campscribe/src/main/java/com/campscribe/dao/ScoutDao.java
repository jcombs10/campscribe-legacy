package com.campscribe.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.campscribe.model2.Scout;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public enum ScoutDao {
	INSTANCE;
	
	public List<Scout> listScouts(String name, String unitType, String unitNumber) {
		Objectify ofy = ObjectifyService.begin();
		Query<Scout> q = ofy.query(Scout.class).order("lastName").order("firstName");
		if (StringUtils.isNotEmpty(name)) {
			q = q.filter("lastName >=", name).filter("lastName <", "u’"+name+"’ + u’\ufffd’");
		}
		if (StringUtils.isNotEmpty(unitType)) {
			q = q.filter("unitType >=", unitType);
		}
		if (StringUtils.isNotEmpty(unitNumber)) {
			q = q.filter("unitNumber >=", unitNumber);
		}
		List<Scout> allScouts = new ArrayList<Scout>();
		for (Scout s: q) {
			allScouts.add(s);
		}
		return allScouts;
	}
	
	public Key<Scout> add(Scout s) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			return ofy.put(s);
		}
	}

	public Scout get(long id) {
		Objectify ofy = ObjectifyService.begin();
		Scout s = ofy.get(Scout.class, id);
		return s;
	}

	public Scout get(String firstName, String lastName, String unitType,
			String unitNumber) {
		Objectify ofy = ObjectifyService.begin();
		Scout s = ofy.query(Scout.class).filter("firstName", firstName).filter("lastName", lastName).filter("unitType", unitType).filter("unitNumber", unitNumber).get();
		return s;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Scout> s = new Key<Scout>(Scout.class, id);
		ofy.delete(s);
	}

}
