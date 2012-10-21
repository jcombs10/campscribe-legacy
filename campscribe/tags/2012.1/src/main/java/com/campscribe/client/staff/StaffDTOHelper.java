package com.campscribe.client.staff;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.shared.StaffDTO;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class StaffDTOHelper {
	public static List<StaffDTO> parseStaffListJsonData(String json) {
		
		List<StaffDTO> badges = new ArrayList<StaffDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray mbArray = value.isArray();

//		Window.alert("Got response: " + json);
		if (mbArray != null) {
			for (int i=0; i<=mbArray.size()-1; i++) {
				JSONObject mbObj = mbArray.get(i).isObject();

				String name = mbObj.get("name").isString().stringValue();
				double id = mbObj.get("id").isNumber().doubleValue();

				StaffDTO b = new StaffDTO();
				b.setName(name);
				Double d = Double.valueOf(id);
				b.setId(d.longValue());
				
				badges.add(b);
			}

		}
		
		return badges;
	}

}
