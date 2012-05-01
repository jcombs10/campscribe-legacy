package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.StaffDao;
import com.campscribe.model2.Staff;
import com.googlecode.objectify.ObjectifyService;

public class StaffManager {
	
	private static boolean registered = false;

	public void addStaff(Staff s) {
		if (!registered) {
			ObjectifyService.register(Staff.class);
			registered = true;
		}

		StaffDao.INSTANCE.add(s);
	}

	public void deleteStaff(long id) {
		if (!registered) {
			ObjectifyService.register(Staff.class);
			registered = true;
		}

		StaffDao.INSTANCE.remove(id);
	}

	public Staff getStaff(long id) {
		if (!registered) {
			ObjectifyService.register(Staff.class);
			registered = true;
		}

		return StaffDao.INSTANCE.get(id);
	}

	public List<Staff> listStaff() {
		if (!registered) {
			ObjectifyService.register(Staff.class);
			registered = true;
		}

		return StaffDao.INSTANCE.listStaff();
	}

	public void updateStaff(Staff e) {
		if (!registered) {
			ObjectifyService.register(Staff.class);
			registered = true;
		}

		StaffDao.INSTANCE.update(e);
	}

}
