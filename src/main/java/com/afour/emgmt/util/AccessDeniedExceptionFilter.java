package com.afour.emgmt.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

public class AccessDeniedExceptionFilter extends OncePerRequestFilter {

	private final String ACCESS_DENIED_REDIRECT = "auth/access-denied";

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc)
			throws ServletException, IOException {
		try {
			fc.doFilter(request, response);
		} catch (AccessDeniedException e) {
			// log error if needed here then redirect
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(ACCESS_DENIED_REDIRECT);
			requestDispatcher.forward(request, response);

		}

	}
}
