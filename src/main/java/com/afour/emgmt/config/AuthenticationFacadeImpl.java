/**
 * 
 */
package com.afour.emgmt.config;

import com.afour.emgmt.common.Actor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getActor() {
		return getAuthentication() != null ?
				getAuthentication().getName() : Actor.DEFAULT_USER;
	}

}
