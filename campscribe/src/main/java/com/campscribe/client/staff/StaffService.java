package com.campscribe.client.staff;

import com.campscribe.shared.StaffDTO;
import com.google.gwt.http.client.RequestCallback;


public interface StaffService {

    public void addStaff(StaffDTO s);

    public void getStaff(String s, RequestCallback cb);

    public void getStaffList(RequestCallback cb);
    
    public void updateStaff(StaffDTO s);

}
