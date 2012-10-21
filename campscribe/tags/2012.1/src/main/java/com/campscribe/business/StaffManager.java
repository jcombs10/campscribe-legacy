package com.campscribe.business;

import java.util.List;
import java.util.Map;

import com.campscribe.dao.StaffDao;
import com.campscribe.model.Staff;
import com.googlecode.objectify.Key;

public class StaffManager extends BaseManager {
	
	public void addStaff(Staff s) {
		StaffDao.INSTANCE.add(s);
	}

	public void deleteStaff(long id) {
		StaffDao.INSTANCE.remove(id);
	}

	public Map<Key<Staff>, Staff> getLookup() {
		return StaffDao.INSTANCE.getLookup();
	}

	public Staff getStaff(long id) {
		return StaffDao.INSTANCE.get(id);
	}

	public Staff getStaffByName(String name) {
		return StaffDao.INSTANCE.getByUserName(name);
	}

	public List<Staff> listStaff() {
		return StaffDao.INSTANCE.listStaff();
	}

	public void updateStaff(Staff e) {
		StaffDao.INSTANCE.update(e);
	}

}
