/**
 * 
 */
package com.afour.emgmt.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
		log.error(exc.getMessage());
//        response.sendRedirect("/auth/access-denied");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "You have insufficient permissions to access this resource.");
    }
}
