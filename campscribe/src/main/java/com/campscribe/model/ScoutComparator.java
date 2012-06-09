package com.campscribe.model;

import java.util.Comparator;

public class ScoutComparator implements Comparator<Scout> {

	@Override
	public int compare(Scout o1, Scout o2) {
		if (!o1.getUnitType().equals(o2.getUnitType())) {
			return o1.getUnitType().compareToIgnoreCase(o2.getUnitType());
		}
		if (!o1.getUnitNumber().equals(o2.getUnitNumber())) {
			return Long.valueOf(o1.getUnitNumber()).compareTo(Long.valueOf(o2.getUnitNumber()));
		}
		if (!o1.getLastName().equals(o2.getLastName())) {
			return o1.getLastName().compareToIgnoreCase(o2.getLastName());
		}
		if (!o1.getFirstName().equals(o2.getFirstName())) {
			return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
		}
		return o1.getRank().compareToIgnoreCase(o2.getRank());
	}

}
