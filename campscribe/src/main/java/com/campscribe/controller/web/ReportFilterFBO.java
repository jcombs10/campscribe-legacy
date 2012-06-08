package com.campscribe.controller.web;

public class ReportFilterFBO {
	private Long eventId;

	public ReportFilterFBO() {
	}

	public ReportFilterFBO(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	} 

}
