package com.campscribe.shared;

import java.util.ArrayList;
import java.util.List;



public class TrackProgressWrapperDTO {
	String comments;
	List<TrackProgressDTO> trackingList = new ArrayList<TrackProgressDTO>();

	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public List<TrackProgressDTO> getTrackingList() {
		return trackingList;
	}
	
	public void setTrackingList(List<TrackProgressDTO> trackingList) {
		this.trackingList = trackingList;
	}

	
}

