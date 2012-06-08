package com.campscribe.controller.web;

public class ReportFilterFBO {
	private Long eventId;
	private String programArea;

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

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

}
