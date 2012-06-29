package com.campscribe.client.clazzes;

import com.campscribe.shared.ScoutDTO;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ScoutDTOHelper {

	public static ScoutDTO parseScoutJsonData(String json) {

		ScoutDTO s = new ScoutDTO();

		JSONValue value = JSONParser.parseLenient(json);

		//		Window.alert("Got response: " + json);
		JSONObject sObj = value.isObject();

		double id = sObj.get("id").isNumber().doubleValue();
		Double d = Double.valueOf(id);
		String firstName = sObj.get("firstName").isString().stringValue();
		String lastName = sObj.get("lastName").isString().stringValue();
		String rank = sObj.get("rank").isString().stringValue();
		String unitType = sObj.get("unitType").isString().stringValue();
		String unitNumber = sObj.get("unitNumber").isString().stringValue();
		double eventId = sObj.get("eventId").isNumber().doubleValue();
		Double d2 = Double.valueOf(eventId);

		s.setId(d.longValue());
		s.setFirstName(firstName);
		s.setLastName(lastName);
		s.setRank(rank);
		s.setUnitType(unitType);
		s.setUnitNumber(unitNumber);
		s.setgetEventId(d2.longValue());

		return s;
	}

	public static String buildJSON(ScoutDTO s) {
		StringBuilder sb = new StringBuilder("{ \"id\":");
		sb.append(s.getId());
		sb.append(",\"firstName\":\"");
		sb.append(s.getFirstName());
		sb.append("\",\"lastName\":\"");
		sb.append(s.getLastName());
		sb.append("\",\"rank\":\"");
		sb.append(s.getRank());
		sb.append("\",\"unitType\":\"");
		sb.append(s.getUnitType());
		sb.append("\",\"unitNumber\":\"");
		sb.append(s.getUnitNumber());
		sb.append("\",\"eventId\":");
		sb.append(s.getEventId());
		sb.append("}");
		return sb.toString();
	}

}
