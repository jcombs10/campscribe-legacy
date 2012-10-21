package com.campscribe.client.scouts;

import java.util.logging.Logger;

import com.campscribe.client.clazzes.ScoutDTOHelper;
import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class ScoutServiceJSONImpl implements ScoutService {

	private Logger logger = Logger.getLogger("ScoutServiceJSONImpl");

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
		rb.setRequestData(ScoutDTOHelper.buildJSON(s));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	@Override
	public void deleteScout(String id) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, "/service/scouts/"+id);
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
//				Window.alert("received response "+response.getStatusCode());
				logger.fine("deleteScout received response "+response.getStatusCode());
				Window.Location.reload();
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

		rb.setRequestData("{}");

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error Occurred: " + e.getMessage());
		}

	}

	@Override
	public void getScouts(String eventId, String unit, RequestCallback callback) {
		StringBuilder url = new StringBuilder("/service/events/");
		url.append(eventId);
		url.append("/units");
		if (!"ALL".equals(unit)) {
			url.append("/");
			url.append(unit);
		}
		url.append("/scouts");
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

	@Override
	public void getUnits(String eventId, RequestCallback callback) {
		StringBuilder url = new StringBuilder("/service/events/");
		url.append(eventId);
		url.append("/units");
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
