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

		double id = ciObj.get("id").isNumber().doubleValue();
		Double d = Double.valueOf(id);
		String campName = ciObj.get("campName").isString().stringValue();
		String address = ciObj.get("address").isString().stringValue();
		String city = ciObj.get("city").isString().stringValue();
		String state = ciObj.get("state").isString().stringValue();
		String zip = ciObj.get("zip").isString().stringValue();
		String phoneNbr = ciObj.get("phoneNbr").isString().stringValue();
		String meritBadgeSigner = ciObj.get("meritBadgeSigner").isString().stringValue();

		ci.setId(d.longValue());
		ci.setCampName(campName);
		ci.setAddress(address);
		ci.setCity(city);
		ci.setState(state);
		ci.setZip(zip);
		ci.setPhoneNbr(phoneNbr);
		ci.setMeritBadgeSigner(meritBadgeSigner);

		return ci;
	}

}
