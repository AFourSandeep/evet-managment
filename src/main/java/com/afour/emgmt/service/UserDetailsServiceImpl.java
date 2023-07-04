/**
 * 
 */
package com.afour.emgmt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.afour.emgmt.entity.Organizer;
import com.afour.emgmt.model.UserInfoUserDetails;
import com.afour.emgmt.repository.OrganizerRepository;

/**
 * 
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	OrganizerRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Organizer> user = userRepository.findByUserName(username);
//		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
//		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
//				grantedAuthorities);
		UserInfoUserDetails userDetail = user.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		return userDetail;
	}

}
