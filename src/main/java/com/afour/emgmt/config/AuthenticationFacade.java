/**
 * 
 */
package com.afour.emgmt.config;

import org.springframework.security.core.Authentication;

/**
 * 
 */
public interface AuthenticationFacade {
	Authentication getAuthentication();

	String getActor();
}
