package com.campscribe.shared;

import java.util.ArrayList;
import java.util.List;

public class TrackProgressWrapperDTO {
	String comments;
	List<TrackProgressDTO> trackingList = new ArrayList<TrackProgressDTO>();
	List<NoteDTO> notesList = new ArrayList<NoteDTO>();

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

	public List<NoteDTO> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<NoteDTO> notesList) {
		this.notesList = notesList;
	}

	
}

