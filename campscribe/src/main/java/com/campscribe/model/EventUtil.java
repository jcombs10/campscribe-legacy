package com.campscribe.model;

import java.util.Date;
import java.util.List;

public class EventUtil {

	public static Long findCurrentEventId(List<Event> events) {
		long eventId = (events.size()>0?events.get(0).getId():0);

		Date today = new Date();
		for (Event e:events) {
			Date startDate = (Date) e.getStartDate().clone();
			startDate.setDate(startDate.getDate()-2);
			Date endDate = (Date) e.getEndDate().clone();
			endDate.setDate(endDate.getDate()+2);
			if (today.after(startDate) && today.before(endDate)) {
				eventId = e.getId();
			}
		}
		
		return eventId;
	}

}
