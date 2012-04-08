package com.campscribe.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class CampScribeUser implements UserDetails {

	private static final long serialVersionUID = 486024279607498866L;

	private String password;
	private String userName;
	private List<String> roles;

	public CampScribeUser(String userName, String password, List<String> roles) {
		super();
		this.password = password==null?"":password;
		this.userName = userName==null?"":userName;
		this.roles = roles==null?new ArrayList<String>():roles;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role: roles) {
			authorities.add(new GrantedAuthorityImpl(role));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
