package com.campscribe.model;

public class Unit {

	private String unitType = "";
	private String unitNumber = "";

	public Unit(String unitType, String unitNumber) {
		this.unitType = unitType;
		this.unitNumber = unitNumber;
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

	@Override
	public String toString() {
		return unitType+" "+unitNumber;
	}
	
	

}
