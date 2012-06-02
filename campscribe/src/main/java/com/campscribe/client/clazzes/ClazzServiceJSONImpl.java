package com.campscribe.client.clazzes;

import java.util.List;
import java.util.logging.Logger;

import com.campscribe.shared.ClazzDTO;
import com.campscribe.shared.ScoutDTO;
import com.campscribe.shared.TrackProgressDTO;
import com.campscribe.shared.TrackProgressDTO.DateAttendanceDTO;
import com.campscribe.shared.TrackProgressDTO.RequirementCompletionDTO;
import com.campscribe.shared.TrackProgressWrapperDTO;
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
		sb.append("\",\"programArea\":\"");
		sb.append(c.getProgramArea());
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
    
    public void getClazzTracking(Long eventId, Long clazzId, RequestCallback callback) {
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

	public void updateClazzTracking(Long eventId, Long clazzId, TrackProgressWrapperDTO data) {
		log.info("Getting /service/events/"+eventId+"/classes/"+clazzId+"/progress");
		RequestBuilder rb = new RequestBuilder(RequestBuilder.PUT, "/service/events/"+eventId+"/classes/"+clazzId+"/progress");
		rb.setHeader("Content-Type","application/json");
		rb.setHeader("Accept","application/json");

		rb.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
//				Window.alert("received response "+response.getStatusCode());
//				Window.Location.reload();
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Error Occurred: " + exception.getMessage());
			}

		});

//		Window.alert("sending  tracking " + buildTrackingJSON(data));
		rb.setRequestData(buildTrackingJSON(data));

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error Occurred: " + ex.getMessage());
		}
	}

	private String buildTrackingJSON(TrackProgressWrapperDTO data) {
		int i = 0;
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"comments\":\""+data.getComments()+"\",");
		sb.append("\"trackingList\":[");
		for (TrackProgressDTO t:data.getTrackingList()) {
			if (i > 0) {
				sb.append(", ");
			} else {
				i++;
			}
			sb.append("{");
			sb.append("\"id\":"+t.getId()+",");
			sb.append("\"scout\":{");
			sb.append("\"id\":"+t.getScout().getId()+",");
			sb.append("\"firstName\":\""+t.getScout().getFirstName()+"\",");
			sb.append("\"lastName\":\""+t.getScout().getLastName()+"\"");
			sb.append("}, \"attendanceList\":[");
			int j = 0;
			for (DateAttendanceDTO da:t.getAttendanceList()) {
				if (j > 0) {
					sb.append(", ");
				} else {
					j++;
				}
				sb.append("{");
				sb.append("\"date\":"+da.getDate().getTime()+",");
				sb.append("\"present\":"+(da.isPresent()?"true":"false"));
				sb.append("}");
			}
			sb.append("], \"requirementList\":[");
			j = 0;
			for (RequirementCompletionDTO da:t.getRequirementList()) {
				if (j > 0) {
					sb.append(", ");
				} else {
					j++;
				}
				sb.append("{");
				sb.append("\"reqNumber\":\""+da.getReqNumber()+"\",");
				sb.append("\"completed\":"+(da.isCompleted()?"true":"false"));
				sb.append("}");
			}
			sb.append("]");
			sb.append("}");
		}
		sb.append("]}");
		return sb.toString();
	}

}
