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
	Key<Clazz> clazzKey;
	Key<Scout> scoutKey;
	@Embedded
	List<DateAttendance> attendanceList = new ArrayList<DateAttendance>();

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
	
}

