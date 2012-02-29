package com.campscribe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.time.FastDateFormat;

import com.google.appengine.api.datastore.Key;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Key id;
    private String description;
	private Date startDate;
	private Date endDate;
	
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Clazz> clazzes = new ArrayList<Clazz>();
	
	private FastDateFormat formatter = null;
	
	public Event(String description, Date startDate, Date endDate) {
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.formatter = FastDateFormat.getInstance("MM/dd/yyyy");
	}
	
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStartDateDisplayStr() {
		return startDate==null?"":getFormatter().format(startDate);
	}

	private FastDateFormat getFormatter() {
		if (formatter == null) {
			formatter = FastDateFormat.getInstance("MM/dd/yyyy");
		}
		return formatter;
	}

	public String getEndDateDisplayStr() {
		return endDate==null?"":getFormatter().format(endDate);
	}

	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	public void addClazz(Clazz c) {
		if (clazzes == null) {
			clazzes = new ArrayList<Clazz>();
		}
		clazzes.add(c);
	}

}
