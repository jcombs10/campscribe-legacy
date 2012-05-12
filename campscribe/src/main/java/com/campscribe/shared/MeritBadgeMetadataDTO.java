package com.campscribe.shared;

public class MeritBadgeMetadataDTO {
	
	private Long id = Long.valueOf(-1);
	private Long mbId;
	private String mbName;
	private Long staffId;
	private String programArea = "";
	
	public MeritBadgeMetadataDTO() {
	}
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	
    public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

}
