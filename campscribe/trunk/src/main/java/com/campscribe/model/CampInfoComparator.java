package com.campscribe.model;

import java.util.Comparator;

public class CampInfoComparator implements Comparator<CampInfo> {

	@Override
	public int compare(CampInfo o1, CampInfo o2) {
		if (!o1.getCampName().equalsIgnoreCase(o2.getCampName())) {
			return o1.getCampName().compareToIgnoreCase(o2.getCampName());
		}
		if (!o1.getAddress().equalsIgnoreCase(o2.getAddress())) {
			return o1.getAddress().compareToIgnoreCase(o2.getAddress());
		}
		if (!o1.getCity().equalsIgnoreCase(o2.getCity())) {
			return o1.getCity().compareToIgnoreCase(o2.getCity());
		}
		if (!o1.getState().equalsIgnoreCase(o2.getState())) {
			return o1.getState().compareToIgnoreCase(o2.getState());
		}
		if (!o1.getZip().equalsIgnoreCase(o2.getZip())) {
			return o1.getZip().compareToIgnoreCase(o2.getZip());
		}
		return o1.getMeritBadgeSigner().compareToIgnoreCase(o2.getMeritBadgeSigner());
	}

}
