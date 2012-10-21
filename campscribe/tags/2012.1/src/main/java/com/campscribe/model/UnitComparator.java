package com.campscribe.model;

import java.util.Comparator;

public class UnitComparator implements Comparator<Unit> {

	@Override
	public int compare(Unit o1, Unit o2) {
		if (!o1.getUnitType().equals(o2.getUnitType())) {
			return o1.getUnitType().compareToIgnoreCase(o2.getUnitType());
		}
		try {
			return Long.valueOf(o1.getUnitNumber()).compareTo(Long.valueOf(o2.getUnitNumber()));
		} catch(NumberFormatException nfe) {
			//couldn't convert unit number to a long, might be a string like "Individual Camper"
			//so fall back to a string comparison
			return o1.getUnitNumber().compareTo(o2.getUnitNumber());
		}
	}

}
