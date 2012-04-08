package com.campscribe.shared;

import java.util.Date;
import java.util.List;


public class TrackProgressDTO {
	private Date startDate;
	private Date endDate;
	private String mbName;
	Long mbId;
	List<ScoutDTO> scouts; 

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

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public List<ScoutDTO> getScouts() {
		return scouts;
	}

	public void setScouts(List<ScoutDTO> scouts) {
		this.scouts = scouts;
	}

}
