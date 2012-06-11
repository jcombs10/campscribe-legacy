package com.campscribe.client.campinfo;

import com.campscribe.shared.CampInfoDTO;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class CampInfoDTOHelper {

	public static CampInfoDTO parseCampInfoJsonData(String json) {

		CampInfoDTO ci = new CampInfoDTO();

		JSONValue value = JSONParser.parseLenient(json);

		//		Window.alert("Got response: " + json);
		JSONObject ciObj = value.isObject();

		String campName = ciObj.get("campName").isString().stringValue();
		String address = ciObj.get("address").isString().stringValue();
		String city = ciObj.get("city").isString().stringValue();
		String state = ciObj.get("state").isString().stringValue();
		String zip = ciObj.get("zip").isString().stringValue();
		String meritBadgeSigner = ciObj.get("meritBadgeSigner").isString().stringValue();

		ci.setCampName(campName);
		ci.setAddress(address);
		ci.setCity(city);
		ci.setState(state);
		ci.setZip(zip);
		ci.setMeritBadgeSigner(meritBadgeSigner);

		return ci;
	}

}
