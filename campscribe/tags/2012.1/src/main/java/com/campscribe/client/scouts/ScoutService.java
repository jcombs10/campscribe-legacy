package com.campscribe.client.scouts;

import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.RequestCallback;


public interface ScoutService {

    public void addScout(ScoutDTO s);
    
    public void deleteScout(String s);

    public void getScouts(String eventId, String unit, RequestCallback callback);

	public void getUnits(String eventId, RequestCallback callback);

}
