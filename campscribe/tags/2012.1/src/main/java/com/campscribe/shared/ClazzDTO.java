package com.campscribe.shared;

import java.util.ArrayList;
import java.util.List;


public class ClazzDTO {
    private String key;
	private String description;

    private Long staffId;
	private Long mbId;
    private Long eventId;
	private String programArea;
	List<NoteDTO> notesList = new ArrayList<NoteDTO>();

	public ClazzDTO() {
	}
	
	public ClazzDTO(String description, Long mbId) {
		this.description = description;
		this.mbId = mbId;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMb(Long mbId) {
		this.mbId = mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

    public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

    public List<NoteDTO> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<NoteDTO> notesList) {
		this.notesList = notesList;
	}
	
}
