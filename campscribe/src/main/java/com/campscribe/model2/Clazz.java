package com.campscribe.model2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Clazz {
    @Id
    private Long id;
	private String description;

    private Long staffId;
	private Long mbId;
    @Parent
    private Key<Event> event;
    
    private List<Long> scoutIds = new ArrayList<Long>();

	public Clazz() {
	}
	
	public Clazz(String description, Long mbId) {
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

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

//	public Event getEvent() {
//		return event;
//	}
//
//	public void setEvent(Event event) {
//		this.event = event;
//	}
//
    public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public List<Long> getScoutIds() {
		return scoutIds;
	}

	public void setScoutIds(List<Long> scoutIds) {
		this.scoutIds = scoutIds;
	}

//	@Transient
//	public String getEncodedKey() {
//		return KeyFactory.keyToString(id);
//	}
//
}
