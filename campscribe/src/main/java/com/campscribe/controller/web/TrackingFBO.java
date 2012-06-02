package com.campscribe.controller.web;

public class TrackingFBO {
	private Long eventId;

	public TrackingFBO() {
	}

	public TrackingFBO(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	} 

}
