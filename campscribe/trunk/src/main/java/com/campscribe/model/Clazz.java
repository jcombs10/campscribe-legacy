package com.campscribe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

@Cached
@Entity
public class Clazz {
	@Id
    private Long id;
	private String description;
	private String programArea;
    private Key<Staff> staffId;
	private Key<MeritBadge> mbId;
	private String mbName;
	@Embedded
	List<Note> notesList = new ArrayList<Note>();
	@Parent
    private Key<Event> event;
    
    private List<Key<Scout>> scoutIds = new ArrayList<Key<Scout>>();

	public Clazz() {
	}
	
	public Clazz(String description, Key<MeritBadge> mbId) {
		this.description = description;
		this.mbId = mbId;
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

	public Key<MeritBadge> getMbId() {
		return mbId;
	}

	public void setMbId(Key<MeritBadge> mbId) {
		this.mbId = mbId;
	}

    public Key<Staff> getStaffId() {
		return staffId;
	}

	public void setStaffId(Key<Staff> staffId) {
		this.staffId = staffId;
	}

	public List<Key<Scout>> getScoutIds() {
		return scoutIds;
	}

	public void setScoutIds(List<Key<Scout>> scoutIds) {
		this.scoutIds = scoutIds;
	}

	public Key<Event> getEvent() {
		return event;
	}

	public void setEvent(Key<Event> event) {
		this.event = event;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

    public List<Note> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<Note> notesList) {
		this.notesList = notesList;
	}
	
	public void addNote(Note note) {
		notesList.add(note);
	}

	public static class Note {
		private Key<Staff> staffKey;
		private String staffName;
		private Date date;
		private String noteText;
		
		public Note() {
		}

		public Key<Staff> getStaffKey() {
			return staffKey;
		}

		public void setStaffKey(Key<Staff> staffKey) {
			this.staffKey = staffKey;
		}

		public String getStaffName() {
			return staffName;
		}

		public void setStaffName(String staffName) {
			this.staffName = staffName;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getNoteText() {
			return noteText;
		}

		public void setNoteText(String noteText) {
			this.noteText = noteText;
		}
		
	}
	
}
