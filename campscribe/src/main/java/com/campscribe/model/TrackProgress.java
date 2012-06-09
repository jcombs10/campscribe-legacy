package com.campscribe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;


@Entity
public class TrackProgress {
    @Id
    private Long id;
	private Key<Clazz> clazzKey;
	private Key<Scout> scoutKey;
	private Boolean complete;
	@Embedded
	private List<DateAttendance> attendanceList = new ArrayList<DateAttendance>();
	@Embedded
	private List<RequirementCompletion> requirementList = new ArrayList<RequirementCompletion>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<Scout> getScoutKey() {
		return scoutKey;
	}

	public void setScoutKey(Key<Scout> scoutKey) {
		this.scoutKey = scoutKey;
	}

	public List<DateAttendance> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<DateAttendance> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public Key<Clazz> getClazzKey() {
		return clazzKey;
	}

	public void setClazzKey(Key<Clazz> clazzKey) {
		this.clazzKey = clazzKey;
	}

	public List<RequirementCompletion> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<RequirementCompletion> requirementList) {
		this.requirementList = requirementList;
	}

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public static class DateAttendance {
		private Date date;
		private boolean present = false;
		
		public DateAttendance() {
			
		}
		
		public Date getDate() {
			return date;
		}
		
		public void setDate(Date date) {
			this.date = date;
		}
		
		public boolean isPresent() {
			return present;
		}
		
		public void setPresent(boolean present) {
			this.present = present;
		}
		
	}
	
	public static class RequirementCompletion {
		private String reqNumber;
		private boolean completed = false;
		
		public RequirementCompletion() {
			
		}

		public String getReqNumber() {
			return reqNumber;
		}

		public void setReqNumber(String reqNumber) {
			this.reqNumber = reqNumber;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}
		
	}
	
}

