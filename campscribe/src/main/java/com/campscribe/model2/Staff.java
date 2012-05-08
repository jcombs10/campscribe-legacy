package com.campscribe.model2;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
@Entity
public class Staff {
	@Id
	private Long id;
	private String name;
	private String userId;
	private String password;
	private List<String> roles;
	private String programArea;
	
	public Staff() {
	}
	
	public Staff(String name, String userId, String password, List<String> roles,
			String programArea) {
		super();
		this.name = name;
		this.userId = userId;
		this.password = password;
		this.roles = roles;
		this.programArea = programArea;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
