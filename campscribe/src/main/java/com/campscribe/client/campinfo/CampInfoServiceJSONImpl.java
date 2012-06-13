package com.campscribe.client.campinfo;

import java.util.logging.Logger;

import com.campscribe.shared.CampInfoDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class CampInfoServiceJSONImpl implements CampInfoService {

	private Logger logger = Logger.getLogger("CampInfoServiceJSONImpl");

	public CampInfoServiceJSONImpl() {
	}

	@Override
    public void getCampInfo(RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/campInfo/");
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
	public void updateStaff(CampInfoDTO data) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.PUT, "/service/campInfo/");
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
		rb.setRequestData(buildJSON(data));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	private String buildJSON(CampInfoDTO data) {
		StringBuilder sb = new StringBuilder("{ \"id\":");
		sb.append(data.getId());
		sb.append(",\"campName\":\"");
		sb.append(data.getCampName());
		sb.append("\",\"address\":\"");
		sb.append(data.getAddress());
		sb.append("\",\"city\":\"");
		sb.append(data.getCity());
		sb.append("\",\"state\":\"");
		sb.append(data.getState());
		sb.append("\",\"zip\":\"");
		sb.append(data.getZip());
		sb.append("\",\"phoneNbr\":\"");
		sb.append(data.getPhoneNbr());
		sb.append("\",\"meritBadgeSigner\":\"");
		sb.append(data.getMeritBadgeSigner());
		sb.append("\"}");
		return sb.toString();
	}

}
