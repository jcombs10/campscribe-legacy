package com.campscribe.model2;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.time.FastDateFormat;

@Entity
public class Event {
    @Id
    private Long id;
    private String description;
	private Date startDate;
	private Date endDate;
//	private ArrayList<Key<Clazz>> clazzes = new ArrayList<Key<Clazz>>();
	
	@Transient
	private FastDateFormat formatter = null;
	
	public Event() {
	}
	
	public Event(String description, Date startDate, Date endDate) {
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.formatter = FastDateFormat.getInstance("MM/dd/yyyy");
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Transient
	public String getStartDateDisplayStr() {
		return startDate==null?"":getFormatter().format(startDate);
	}

	@Transient
	public String getEndDateDisplayStr() {
		return endDate==null?"":getFormatter().format(endDate);
	}

	@Transient
	private FastDateFormat getFormatter() {
		if (formatter == null) {
			formatter = FastDateFormat.getInstance("MM/dd/yyyy");
		}
		return formatter;
	}

//	public ArrayList<Key<Clazz>> getClazzes() {
//		return clazzes;
//	}
//
//	public void setClazzes(ArrayList<Key<Clazz>> clazzes) {
//		this.clazzes = clazzes;
//	}
//
//	public void addClazz(Key<Clazz> c) {
//		if (clazzes == null) {
//			clazzes = new ArrayList<Key<Clazz>>();
//		}
//		clazzes.add(c);
//	}
//
//	@Transient
//	public String getEncodedKey() {
//		return KeyFactory.keyToString(id);
//	}
//
}
