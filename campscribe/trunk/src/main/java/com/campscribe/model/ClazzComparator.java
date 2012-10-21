package com.campscribe.model;

import java.util.Comparator;
import java.util.Date;

public class ClazzComparator implements Comparator<Clazz> {

	@Override
	public int compare(Clazz o1, Clazz o2) {
		Date d1 = new Date();
		Date d2 = new Date(d1.getTime());

		String[] o1StartParts = o1.getDescription().split(":");
		String[] o2StartParts = o2.getDescription().split(":");

		String startHour1 = o1StartParts[0];
		String startHour2 = o2StartParts[0];
		String startMinute1 = o1StartParts[1].split("-")[0];
		String startMinute2 = o2StartParts[1].split("-")[0];
		String endHour1 = o1StartParts[1].split("-")[1];
		String endHour2 = o2StartParts[1].split("-")[1];
		String endMinute1 = o1StartParts[2];
		String endMinute2 = o2StartParts[2];

		if (!startHour1.equals(startHour2)) {
			int hoursInt1 = Integer.parseInt(startHour1);
			if (hoursInt1 < 9) {
				hoursInt1 += 12;
			}
			int hoursInt2 = Integer.parseInt(startHour2);
			if (hoursInt2 < 9) {
				hoursInt2 += 12;
			}
			d1.setHours(hoursInt1);
			d2.setHours(hoursInt2);
			return d1.compareTo(d2);
		} else if (!startMinute1.equals(startMinute2)) {
			int minutesInt1 = Integer.parseInt(startMinute1);
			int minutesInt2 = Integer.parseInt(startMinute2);
			d1.setMinutes(minutesInt1);
			d2.setMinutes(minutesInt2);
			return d1.compareTo(d2);
		} else if (!endHour1.equals(endHour2)) {
			int hoursInt1 = Integer.parseInt(endHour1);
			if (hoursInt1 < 9) {
				hoursInt1 += 12;
			}
			int hoursInt2 = Integer.parseInt(endHour2);
			if (hoursInt2 < 9) {
				hoursInt2 += 12;
			}
			d1.setHours(hoursInt1);
			d2.setHours(hoursInt2);
			return d1.compareTo(d2);
		} else if (!endMinute1.equals(endMinute2)) {
			int minutesInt1 = Integer.parseInt(endMinute1);
			int minutesInt2 = Integer.parseInt(endMinute2);
			d1.setMinutes(minutesInt1);
			d2.setMinutes(minutesInt2);
			return d1.compareTo(d2);
		} else {
			return o1.getMbName().compareTo(o2.getMbName());
		}
	}

}
