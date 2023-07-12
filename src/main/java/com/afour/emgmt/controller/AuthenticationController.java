/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.common.AppResponse;
import com.afour.emgmt.common.AuthRequest;
import com.afour.emgmt.common.AppResponseBuilder;
import com.afour.emgmt.common.LoginResponse;
import com.afour.emgmt.model.UserInfoUserDetails;
import com.afour.emgmt.service.JwtService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@ApiIgnore
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	AppResponseBuilder responseBuilder;
	
	@PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		LoginResponse response;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
				response = LoginResponse.builder().username(user.getUsername()).status(HttpStatus.OK)
						.message("Login Success.").authorities((List<GrantedAuthority>) user.getAuthorities())
						.token(jwtService.generateToken(authRequest.getUsername())).build();
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new UsernameNotFoundException("invalid user request !");
			}
		} catch (BadCredentialsException ex) {
			response = LoginResponse.builder().username(authRequest.getUsername())
					.message("Login Fail.").build();
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping(value = "/access-denied", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppResponse> accessDeniedHandler() {
		return new ResponseEntity(responseBuilder.getAccessDeniedResponse(), HttpStatus.UNAUTHORIZED);
	}

}
