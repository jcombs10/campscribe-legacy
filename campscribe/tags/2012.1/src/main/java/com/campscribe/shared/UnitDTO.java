package com.campscribe.shared;

public class UnitDTO {

	private String unitType = "";
	private String unitNumber = "";

	public UnitDTO(String unitType, String unitNumber) {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((unitNumber == null) ? 0 : unitNumber.hashCode());
		result = prime * result
				+ ((unitType == null) ? 0 : unitType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnitDTO other = (UnitDTO) obj;
		if (unitNumber == null) {
			if (other.unitNumber != null)
				return false;
		} else if (!unitNumber.equals(other.unitNumber))
			return false;
		if (unitType == null) {
			if (other.unitType != null)
				return false;
		} else if (!unitType.equals(other.unitType))
			return false;
		return true;
	}

}
