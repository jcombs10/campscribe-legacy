package com.campscribe.client.staff;

import java.util.logging.Logger;

import com.campscribe.shared.StaffDTO;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class StaffServiceJSONImpl implements StaffService {

	private Logger logger = Logger.getLogger("StaffServiceJSONImpl");

	public StaffServiceJSONImpl() {
	}

	@Override
	public void addStaff(StaffDTO s) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/service/staff/");
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
	public void deleteStaff(String id) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, "/service/staff/"+id);
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
//				Window.alert("received response "+response.getStatusCode());
				logger.fine("deleteStaff received response "+response.getStatusCode());
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
	public void getStaff(String id, RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/staff/"+id);
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
	public void getStaffList(RequestCallback cb) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/service/staff/");
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
	public void updateStaff(StaffDTO s) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.PUT, "/service/staff/"+s.getId());
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

	private String buildJSON(StaffDTO s) {
		StringBuilder sb = new StringBuilder("{ \"id\":\"");
		sb.append(s.getId());
		sb.append("\",\"name\":\"");
		sb.append(s.getName());
		sb.append("\",\"userId\":\"");
		sb.append(s.getUserId());
		sb.append("\",\"password\":\"");
		sb.append(s.getPassword());
		sb.append("\",\"emailAddress\":\"");
		sb.append(s.getEmailAddress());
		sb.append("\",\"roles\":[");
		int i = 0;
		for (String str:s.getRoles()) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append("\"");
			sb.append(str);
			sb.append("\"");
			i++;
		}
		sb.append("],\"programArea\":\"");
		sb.append(s.getProgramArea());
		sb.append("\"}");
		return sb.toString();
	}

}
