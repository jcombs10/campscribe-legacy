package com.campscribe.client.events;

import com.campscribe.shared.EventDTO;
import com.google.gwt.http.client.RequestCallback;


public interface EventService {

    public void addEvent(EventDTO e);

	public void getAllEvents(RequestCallback requestCallback);

	public void getEvent(String id, RequestCallback requestCallback);

	public void updateEvent(EventDTO data);
    
}
