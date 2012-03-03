package com.campscribe.shared;

import java.util.List;

public class StaffDTO {
	public final static String AQUATICS = "Aquatics";
	public final static String HANDICRAFT = "Handicraft";
	public final static String OUTDOOR_SKILLS = "Outdoor Skills";
	
	private Long id = Long.valueOf(-1);
	private String name;
	private String userId;
	private List<String> roles;
	private String programArea;
	
	public StaffDTO() {
	}
	
	public StaffDTO(String name, String userId, List<String> roles,
			String programArea) {
		super();
		this.name = name;
		this.userId = userId;
		this.roles = roles;
		this.programArea = programArea;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
