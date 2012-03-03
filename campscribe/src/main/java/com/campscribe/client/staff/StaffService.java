package com.campscribe.client.staff;

import com.campscribe.shared.StaffDTO;
import com.google.gwt.http.client.RequestCallback;


public interface StaffService {

    public void addStaff(StaffDTO s);

	public void getStaffList(RequestCallback cb);
    
}
