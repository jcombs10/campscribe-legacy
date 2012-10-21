package com.campscribe.controller.web;

public class EventFilterFBO {
	private Long eventId;

	public EventFilterFBO() {
	}

	public EventFilterFBO(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	} 

}
