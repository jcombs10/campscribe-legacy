package com.campscribe.model2;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
@Entity
public class MeritBadgeMetadata {
	
	@Id
	private Long id;
	private Key<MeritBadge> mbKey;
	private Key<Staff> staffKey;
	private String programArea;
	
	public MeritBadgeMetadata() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Key<MeritBadge> getMbKey() {
		return mbKey;
	}

	public void setMbKey(Key<MeritBadge> mbKey) {
		this.mbKey = mbKey;
	}

	public Key<Staff> getStaffKey() {
		return staffKey;
	}

	public void setStaffKey(Key<Staff> staffKey) {
		this.staffKey = staffKey;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

}
