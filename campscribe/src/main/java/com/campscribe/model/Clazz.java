package com.campscribe.model;

import java.util.ArrayList;
import java.util.List;

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
	private String notes;
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

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
