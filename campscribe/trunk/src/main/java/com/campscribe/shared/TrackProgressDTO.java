package com.campscribe.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TrackProgressDTO {
	Long id;
	ScoutDTO scout;
	List<DateAttendanceDTO> attendanceList = new ArrayList<DateAttendanceDTO>();
	List<RequirementCompletionDTO> requirementList = new ArrayList<RequirementCompletionDTO>();

	public ScoutDTO getScout() {
		return scout;
	}

	public void setScout(ScoutDTO scout) {
		this.scout = scout;
	}

	public List<DateAttendanceDTO> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<DateAttendanceDTO> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<RequirementCompletionDTO> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<RequirementCompletionDTO> requirementList) {
		this.requirementList = requirementList;
	}

	public static class DateAttendanceDTO {
		private Date date;
		private boolean present = false;
		
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
	
	public static class RequirementCompletionDTO {
		private String reqNumber;
		private boolean completed = false;
		
		public RequirementCompletionDTO() {
			
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

