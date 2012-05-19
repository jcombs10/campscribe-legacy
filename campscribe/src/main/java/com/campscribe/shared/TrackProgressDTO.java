package com.campscribe.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TrackProgressDTO {
	Long id;
	ScoutDTO scout;
	List<DateAttendanceDTO> attendanceList = new ArrayList<DateAttendanceDTO>();

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
	
}

