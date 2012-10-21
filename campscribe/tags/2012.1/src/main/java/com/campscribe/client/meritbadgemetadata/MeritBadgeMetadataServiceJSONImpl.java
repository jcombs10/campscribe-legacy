package com.campscribe.client.meritbadgemetadata;

import java.util.logging.Logger;

import com.campscribe.shared.MeritBadgeMetadataDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class MeritBadgeMetadataServiceJSONImpl implements MeritBadgeMetadataService {

	private Logger log = Logger.getLogger("MeritBadgeMetadataServiceJSONImpl");

	public MeritBadgeMetadataServiceJSONImpl() {
	}

	@Override
	public void getMeritBadgeMetadata(Long id, RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/meritbadgemetadata/"+id);
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(cb);

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error Occurred: " + e.getMessage());
		}

	}

	@Override
	public void updateMeritBadgeMetadata(MeritBadgeMetadataDTO mb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.PUT, "/service/meritbadgemetadata/"+mb.getId());
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
		rb.setRequestData(buildJSON(mb));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	private String buildJSON(MeritBadgeMetadataDTO mb) {
		StringBuilder sb = new StringBuilder("{ \"id\":");
		sb.append(mb.getId());
		sb.append(",\"mbId\":");
		sb.append(mb.getMbId());
		sb.append(",\"mbName\":\"");
		sb.append(mb.getMbName());
		sb.append("\",\"programArea\":\"");
		sb.append(mb.getProgramArea());
		sb.append("\",\"staffId\":");
		sb.append(mb.getStaffId());
		sb.append("}");
		return sb.toString();
	}

}
