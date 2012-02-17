package com.campscribe.client.meritbadges;

import java.util.logging.Logger;

import com.campscribe.shared.MeritBadgeDTO;
import com.campscribe.shared.RequirementDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class MeritBadgeServiceJSONImpl implements MeritBadgeService {

	private Logger log = Logger.getLogger("MeritBadgeServiceJSONImpl");

	public MeritBadgeServiceJSONImpl() {
	}

	@Override
	public void addMeritBadge(MeritBadgeDTO mb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/meritbadges/");
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
		} catch (RequestException e) {
			Window.alert("Error Occurred: " + e.getMessage());
		}

	}

	@Override
	public void getMeritBadges(RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/meritbadges/");
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(cb);

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error Occurred: " + e.getMessage());
		}

	}

	private String buildJSON(MeritBadgeDTO mb) {
		StringBuilder sb = new StringBuilder("{ \"id\":\"");
		sb.append(mb.getId());
		sb.append("\",\"badgeName\":\"");
		sb.append(mb.getBadgeName());
		sb.append("\",\"bsaAdvancementId\":\"");
		sb.append(mb.getBsaAdvancementId());
		sb.append("\",\"eagleRequired\":\"");
		sb.append(mb.isEagleRequired());
		sb.append("\",\"requirements\":[");
		int i = 0;
		for (RequirementDTO reqDto:mb.getRequirements()) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append("{ \"reqType\":\"");
			sb.append(reqDto.getReqType());
			sb.append("\",\"howManyToChoose\":\"");
			sb.append(reqDto.getHowManyToChoose());
			sb.append("\",\"optionCount\":\"");
			sb.append(reqDto.getOptionCount());
			sb.append("\" }");
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}

}
