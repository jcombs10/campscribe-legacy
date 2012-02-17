package com.campscribe.shared;


public class ClazzDTO {
    private String key;
	private String description;

    private Long mbId;
    private Long eventId;

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

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

}