package com.campscribe.client.clazzes;

import java.util.List;
import java.util.logging.Logger;

import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.ScoutDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class ClazzServiceJSONImpl implements ClazzService {

	private Logger log = Logger.getLogger("ClazzServiceJSONImpl");

	public ClazzServiceJSONImpl() {
	}

	@Override
	public void addClazz(ClazzDTO c) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/events/"+c.getEventId()+"/classes/");
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
		sb.append("\",\"staffId\":\"");
		sb.append(c.getStaffId());
		sb.append("\",\"eventId\":\"");
		sb.append(c.getEventId());
		sb.append("\"}");
		return sb.toString();
	}

	@Override
	public void addScoutToClazz(Long eventId, Long clazzId, List<ScoutDTO> s) {
		log.info("Posting to /service/events/"+eventId+"/classes/"+clazzId);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/events/"+eventId+"/classes/"+clazzId);
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

	@Override
    public void getClazz(Long eventId, Long clazzId, RequestCallback callback) {
		log.info("Getting /service/events/"+eventId+"/classes/"+clazzId+"/progress");
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/events/"+eventId+"/classes/"+clazzId+"/progress");
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(callback);

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

    }
    
	private String buildJSON(List<ScoutDTO> sList) {
		StringBuilder sb = new StringBuilder("[ ");
		boolean firstOne = true;
		for (ScoutDTO s:sList) {
			if (!firstOne) {
				sb.append(", ");
				firstOne = false; 
			}

			sb.append(s.getId());
		}
		sb.append(" ]");
		log.info("built json string "+sb.toString());
		return sb.toString();
	}

}
