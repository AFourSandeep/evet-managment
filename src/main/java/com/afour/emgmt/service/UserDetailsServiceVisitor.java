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

import com.afour.emgmt.entity.Visitor;
import com.afour.emgmt.model.UserInfoUserDetails;
import com.afour.emgmt.repository.VisitorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Component
@Slf4j
public class UserDetailsServiceVisitor implements UserDetailsService {

	@Autowired
	VisitorRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Visitor> user = repository.findByUserName(username);
		log.info("Found the user by {}",username);
//		List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()));
//		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
//				grantedAuthorities);
		UserInfoUserDetails userDetail = user.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		return userDetail;
	}

}
