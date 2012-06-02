package com.campscribe.shared;


public class ScoutDTO {
	public static final String SCOUT = "";
	public static final String TENDERFOOT = "Tenderfoot";
	public static final String SECOND_CLASS = "2nd Class";
	public static final String FIRST_CLASS = "1st Class";
	public static final String STAR = "Star";
	public static final String LIFE = "Life";
	public static final String EAGLE = "Eagle";
	
	public static final String CREW = "Crew";
	public static final String POST = "Post";
	public static final String TEAM = "Team";
	public static final String TROOP = "Troop";

	private Long id = Long.valueOf(-1);
	private String firstName;
	private String lastName;
	private String rank;
	private String unitType;
	private String unitNumber;
	private Long eventId;
	
	public ScoutDTO() {
	}
	
	public ScoutDTO(String firstName, String lastName, String rank, String unitType, String unitNumber, Long eventId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rank = rank;
		this.unitType = unitType;
		this.unitNumber = unitNumber;
		this.eventId = eventId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setgetEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getDisplayName() {
		return firstName + " " + lastName;
	}

}
