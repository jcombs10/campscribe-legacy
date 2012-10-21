package com.campscribe.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Serialized;

@Cached
@Entity
public class MeritBadge {
	
	@Id
	private Long id;
	private String bsaAdvancementId;
	private String badgeName;
	private Boolean eagleRequired = Boolean.FALSE;
	private String requirementsStr;
	@Serialized
	private List<Requirement> requirements = new ArrayList<Requirement>();
	
	public MeritBadge() {
	}
	
	public MeritBadge(String badgeName, Boolean eagleRequired) {
		this.badgeName = badgeName;
		this.eagleRequired = eagleRequired==null?Boolean.FALSE:eagleRequired;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBsaAdvancementId() {
		return bsaAdvancementId;
	}

	public void setBsaAdvancementId(String bsaAdvancementId) {
		this.bsaAdvancementId = bsaAdvancementId;
	}

	public String getBadgeName() {
		return badgeName;
	}
	
	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public boolean isEagleRequired() {
		if (eagleRequired == null) {
			eagleRequired = Boolean.FALSE;
		}
		return eagleRequired;
	}

	public void setEagleRequired(Boolean eagleRequired) {
		this.eagleRequired = eagleRequired==null?Boolean.FALSE:eagleRequired;
	}

	public String getRequirementsStr() {
		return requirementsStr;
	}

	public void setRequirementsStr(String requirementsStr) {
		this.requirementsStr = requirementsStr;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

}
