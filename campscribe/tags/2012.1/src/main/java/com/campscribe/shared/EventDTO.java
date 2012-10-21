package com.campscribe.shared;

import java.util.Date;

public class EventDTO {
	private Long id = Long.valueOf(-1);
	private String description;
	private Date startDate;
	private Date endDate;
	
	public EventDTO() {
	}
	
	public EventDTO(String description, Date startDate, Date endDate) {
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
