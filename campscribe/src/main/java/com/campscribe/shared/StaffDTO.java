package com.campscribe.shared;

import java.util.List;

public class StaffDTO {
	public final static String AQUATICS = "Aquatics";
	public final static String HANDICRAFT = "Handicraft";
	public final static String OUTDOOR_SKILLS = "Outdoor Skills";
	public static final String EAGLE_RIDGE = "Eagle Ridge";
	public static final String COPE_AND_CLIMBING = "COPE and Climbing";
	public static final String HANDYMAN = "Handyman";
	public static final String HEALTH_LODGE = "Health Lodge";
	public static final String NATIVE_AMERICAN_VILLAGE = "Native American Village";
	public static final String NEST = "NEST";
	public static final String SHOOTING_SPORTS = "Shooting Sports";
	
	private Long id = Long.valueOf(-1);
	private String name;
	private String userId;
	private String password;
	private List<String> roles;
	private String programArea;
	
	public StaffDTO() {
	}
	
	public StaffDTO(String name, String userId, String password, List<String> roles,
			String programArea) {
		super();
		this.name = name;
		this.userId = userId;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
