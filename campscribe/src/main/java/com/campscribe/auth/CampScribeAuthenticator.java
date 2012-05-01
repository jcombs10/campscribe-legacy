package com.campscribe.auth;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.campscribe.dao.StaffDao;
import com.campscribe.model2.Staff;

public class CampScribeAuthenticator implements org.springframework.security.core.userdetails.UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		Staff s = StaffDao.INSTANCE.getByUserName(userName);
		
		if (s == null) {
			throw new UsernameNotFoundException("");
		}
		
		UserDetails ud = new CampScribeUser(s.getUserId(), s.getPassword(), s.getRoles());
		
		return ud;
	}

}
