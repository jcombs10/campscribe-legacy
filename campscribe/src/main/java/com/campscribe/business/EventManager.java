package com.campscribe.business;

import java.util.List;

import com.campscribe.dao.EventDao;
import com.campscribe.model.Event;

public class EventManager {
	
	public void addEvent(Event e) {
		EventDao.INSTANCE.add(e);
	}

	public void deleteEvent(long id) {
		EventDao.INSTANCE.remove(id);
	}

	public Event getEvent(long id) {
		return EventDao.INSTANCE.get(id);
	}

	public List<Event> listEvents() {
		return EventDao.INSTANCE.listEvents();
	}

	public void updateEvent(Event e) {
		EventDao.INSTANCE.add(e);
	}

}
