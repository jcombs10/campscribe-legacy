package com.campscribe.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class Clazz {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Key id;
	private String description;

    private Long mbId;
    @ManyToOne(fetch=FetchType.LAZY)
    private Event event;

	public Clazz() {
	}
	
	public Clazz(String description, Long mbId) {
		this.description = description;
		this.mbId = mbId;
	}
	
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
