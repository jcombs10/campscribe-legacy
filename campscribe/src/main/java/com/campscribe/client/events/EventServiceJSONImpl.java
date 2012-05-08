package com.campscribe.client.events;

import java.util.logging.Logger;
import java.util.Date;

import com.campscribe.shared.EventDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;

public class EventServiceJSONImpl implements EventService {

	private Logger log = Logger.getLogger("EventServiceJSONImpl");
	private DateTimeFormat dateFormatterNoTime = DateTimeFormat.getFormat("yyyy-MM-dd");

	public EventServiceJSONImpl() {
	}

	@Override
	public void addEvent(EventDTO e) {
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
		rb.setRequestData(buildJSON(e));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	@Override
	public void getEvent(String id, RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/events/"+id);
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
	public void updateEvent(EventDTO e) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.PUT, "/service/events/"+e.getId());
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

//		Window.alert("sending  Event" + buildJSON(e));
		rb.setRequestData(buildJSON(e));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}

	}

	private String buildJSON(EventDTO e) {
		StringBuilder sb = new StringBuilder("{ \"id\":\"");
		sb.append(e.getId());
		sb.append("\",\"description\":\"");
		sb.append(e.getDescription());
		sb.append("\",\"startDate\":\"");
		sb.append(formatDate(e.getStartDate()));
		sb.append("\",\"endDate\":\"");
		sb.append(formatDate(e.getEndDate()));
		sb.append("\"}");
		return sb.toString();
	}

        private String formatDate(Date d) {
            return dateFormatterNoTime.format(d);
        }

}
