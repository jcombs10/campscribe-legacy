package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.StaffDao;
import com.campscribe.model.Staff;

public class StaffManager {
	
	public void addStaff(Staff s) {
		StaffDao.INSTANCE.add(s);
	}

	public void deleteStaff(long id) {
		StaffDao.INSTANCE.remove(id);
	}

	public Staff getStaff(long id) {
		return StaffDao.INSTANCE.get(id);
	}

	public List<Staff> listStaff() {
		return StaffDao.INSTANCE.listStaff();
	}

	public void updateStaff(Staff e) {
		StaffDao.INSTANCE.add(e);
	}

}
