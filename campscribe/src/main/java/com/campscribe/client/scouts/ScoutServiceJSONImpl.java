package com.campscribe.client.scouts;

import java.util.logging.Logger;

import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

public class ScoutServiceJSONImpl implements ScoutService {

	private Logger log = Logger.getLogger("ScoutServiceJSONImpl");

	public ScoutServiceJSONImpl() {
	}

	@Override
	public void addScout(ScoutDTO s) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/scouts/");
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
//				Window.alert("received response "+response.getStatusCode());
				Window.Location.reload();
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

//		Window.alert("sending  MB" + buildJSON(mb));
		rb.setRequestData(buildJSON(s));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	private String buildJSON(ScoutDTO s) {
		StringBuilder sb = new StringBuilder("{ \"id\":\"");
		sb.append(s.getId());
		sb.append("\",\"firstName\":\"");
		sb.append(s.getFirstName());
		sb.append("\",\"lastName\":\"");
		sb.append(s.getLastName());
		sb.append("\",\"rank\":\"");
		sb.append(s.getRank());
		sb.append("\",\"unitType\":\"");
		sb.append(s.getUnitType());
		sb.append("\",\"unitNumber\":\"");
		sb.append(s.getUnitNumber());
		sb.append("\"}");
		return sb.toString();
	}

	@Override
	public void searchScouts(String name, String unitType, String unitNumber, RequestCallback callback) {
		StringBuilder url = new StringBuilder("/service/scouts/");
		url.append("?name=");
		url.append(name);
		url.append("&unitType=");
		url.append(unitType);
		url.append("&unitNumber=");
		url.append(unitNumber);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url.toString());
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(callback);

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

}
