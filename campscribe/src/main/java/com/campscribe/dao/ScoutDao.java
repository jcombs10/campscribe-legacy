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
		//TODO - sorting
		Objectify ofy = ObjectifyService.begin();
		Query<Scout> q = ofy.query(Scout.class);
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
	
	public void add(Scout s) {
		synchronized(this) {
			Objectify ofy = ObjectifyService.begin();
			ofy.put(s);
		}
	}

	public Scout get(long id) {
		Objectify ofy = ObjectifyService.begin();
		Scout s = ofy.get(Scout.class, id);
		return s;
	}

	public void remove(long id) {
		Objectify ofy = ObjectifyService.begin();
		Key<Scout> s = new Key<Scout>(Scout.class, id);
		ofy.delete(s);
	}

}
