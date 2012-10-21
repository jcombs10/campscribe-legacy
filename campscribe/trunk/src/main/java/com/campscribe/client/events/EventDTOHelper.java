package com.campscribe.client.events;

import java.util.ArrayList;
import java.util.List;

import com.campscribe.shared.EventDTO;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class EventDTOHelper {
	public static List<EventDTO> parseEventListJsonData(String json) {
		
		List<EventDTO> events = new ArrayList<EventDTO>();

		JSONValue value = JSONParser.parseLenient(json);
		JSONArray mbArray = value.isArray();

//		Window.alert("Got response: " + json);
		if (mbArray != null) {
			for (int i=0; i<=mbArray.size()-1; i++) {
				JSONObject mbObj = mbArray.get(i).isObject();

				String description = mbObj.get("description").isString().stringValue();
				double id = mbObj.get("id").isNumber().doubleValue();

				EventDTO e = new EventDTO();
				e.setDescription(description);
				Double d = Double.valueOf(id);
				e.setId(d.longValue());
				
				events.add(e);
			}

		}
		
		return events;
	}

}
