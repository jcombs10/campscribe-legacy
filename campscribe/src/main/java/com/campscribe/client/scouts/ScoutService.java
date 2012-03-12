package com.campscribe.client.scouts;

import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.RequestCallback;


public interface ScoutService {

    public void addScout(ScoutDTO s);
    
    public void searchScouts(String name, String unitType, String unitNumber, RequestCallback callback);
    
}
