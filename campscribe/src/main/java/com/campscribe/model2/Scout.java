package com.campscribe.model2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Scout {
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	private String rank;
	private String unitType;
	private String unitNumber;
	
	public Scout() {
	}
	
	public Scout(String firstName, String lastName, String rank, String unitType, String unitNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rank = rank;
		this.unitType = unitType;
		this.unitNumber = unitNumber;
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

	@Transient
	public String getDisplayName() {
		return firstName + " " + lastName;
	}

}
