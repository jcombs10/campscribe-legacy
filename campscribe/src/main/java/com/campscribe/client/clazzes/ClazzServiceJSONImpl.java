package com.campscribe.client.clazzes;

import java.util.logging.Logger;

import com.campscribe.shared.ClazzDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;

public class ClazzServiceJSONImpl implements ClazzService {

	private Logger log = Logger.getLogger("ClazzServiceJSONImpl");

	public ClazzServiceJSONImpl() {
	}

	@Override
	public void addClazz(ClazzDTO c) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/events/");
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
		rb.setRequestData(buildJSON(c));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	private String buildJSON(ClazzDTO c) {
		StringBuilder sb = new StringBuilder("{ \"key\":\"");
		sb.append(c.getKey());
		sb.append("\",\"description\":\"");
		sb.append(c.getDescription());
		sb.append("\",\"mbId\":\"");
		sb.append(c.getMbId());
		sb.append("\",\"eventId\":\"");
		sb.append(c.getEventId());
		sb.append("\"}");
		return sb.toString();
	}

}
