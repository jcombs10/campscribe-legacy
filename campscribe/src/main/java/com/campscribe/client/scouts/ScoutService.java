package com.campscribe.client.scouts;

import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.RequestCallback;


public interface ScoutService {

    public void addScout(ScoutDTO s);
    
    public void deleteScout(String s);

    public void searchScouts(Long eventId, String name, String unitType, String unitNumber, RequestCallback callback);
    
}
