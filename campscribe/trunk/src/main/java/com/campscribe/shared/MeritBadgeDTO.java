package com.campscribe.shared;

import java.util.ArrayList;
import java.util.List;


public class MeritBadgeDTO {
	
	private Long id = Long.valueOf(-1);
    private String bsaAdvancementId = "";
	private String badgeName = "";
	private Boolean eagleRequired = Boolean.FALSE;
	private String requirementsStr = "";
	private List<RequirementDTO> requirements = new ArrayList<RequirementDTO>();
	
	public MeritBadgeDTO() {
	}
	
	public MeritBadgeDTO(String badgeName, Boolean eagleRequired) {
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

	public List<RequirementDTO> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<RequirementDTO> requirements) {
		this.requirements = requirements;
	}

}
