/**
 * 
 */
package com.afour.emgmt.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	@Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        throw e;
    }
}
