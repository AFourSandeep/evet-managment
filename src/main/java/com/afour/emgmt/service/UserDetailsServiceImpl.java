/**
 * 
 */
package com.afour.emgmt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.afour.emgmt.common.RoleEnum;
import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.model.UserInfoUserDetails;
import com.afour.emgmt.repository.OrganizerRepository;
import com.afour.emgmt.repository.VisitorRepository;

/**
 * 
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	OrganizerRepository orgRepository;
	
	@Autowired
	VisitorRepository visitorRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfoUserDetails userDetail;
		if(username.startsWith(RoleEnum.VISITOR.name())) {
		Optional<Visitor>	user = visitorRepository.findByUserName(username.replaceFirst(RoleEnum.VISITOR.name(), ""));
			userDetail = user.map(UserInfoUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		}
		else {
			Optional<Organizer>	user = orgRepository.findByUserName(username);
			userDetail = user.map(UserInfoUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		}
		return userDetail;
	}

}
