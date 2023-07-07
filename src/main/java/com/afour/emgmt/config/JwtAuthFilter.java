/**
 * 
 */
package com.afour.emgmt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.afour.emgmt.common.AppResponse;
import com.afour.emgmt.service.JwtService;
import com.afour.emgmt.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * 
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	AuthenticationFacade authentication;

	@SuppressWarnings("deprecation")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
					username = jwtService.extractUsername(token);
			} catch (ExpiredJwtException e) {
				AppResponse apiError = AppResponse.builder().message("Token expired please get a new token.")
			    		.body(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.toString());
			}
		}

		if (username != null && authentication.getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
