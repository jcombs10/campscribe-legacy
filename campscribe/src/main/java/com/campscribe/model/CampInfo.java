package com.campscribe.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
@Entity
public class CampInfo {
    @Id
    private Long id;
    private String campName = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	private String phoneNbr = "";
	private String meritBadgeSigner = "";
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCampName() {
		return campName;
	}
	
	public void setCampName(String campName) {
		this.campName = campName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNbr() {
		return phoneNbr;
	}

	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	public String getMeritBadgeSigner() {
		return meritBadgeSigner;
	}

	public void setMeritBadgeSigner(String meritBadgeSigner) {
		this.meritBadgeSigner = meritBadgeSigner;
	}
	
}
