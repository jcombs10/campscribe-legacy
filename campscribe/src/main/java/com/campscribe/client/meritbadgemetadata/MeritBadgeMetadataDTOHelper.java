package com.campscribe.client.meritbadgemetadata;

import com.campscribe.shared.MeritBadgeMetadataDTO;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public class MeritBadgeMetadataDTOHelper {
	public static MeritBadgeMetadataDTO parseMeritBadgeJsonData(String json) {

		JSONValue value = JSONParser.parseLenient(json);

		Window.alert("Got response: " + json);
		JSONObject mbObj = value.isObject();

		double id = mbObj.get("id").isNumber().doubleValue();
		String programArea = mbObj.get("programArea").isString().stringValue();
		String mbName = mbObj.get("mbName").isString().stringValue();
		double mbId = mbObj.get("mbId").isNumber().doubleValue();
		double staffId = mbObj.get("staffId").isNumber().doubleValue();

		MeritBadgeMetadataDTO mbMd = new MeritBadgeMetadataDTO();
		Double d = Double.valueOf(id);
		mbMd.setId(d.longValue());
		d = Double.valueOf(mbId);
		mbMd.setMbId(d.longValue());
		d = Double.valueOf(staffId);
		mbMd.setStaffId(d.longValue());
		mbMd.setProgramArea(programArea);
		mbMd.setMbName(mbName);
		
		return mbMd;
	}

}
