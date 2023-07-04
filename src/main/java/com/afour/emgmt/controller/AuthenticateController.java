/**
 * 
 */
package com.afour.emgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.model.AppResponse;
import com.afour.emgmt.model.AuthRequest;
import com.afour.emgmt.service.JwtService;
import com.afour.emgmt.util.GenericResponse;

/**
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/auth")
public class AuthenticateController {

	@Autowired
	JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	GenericResponse genericResponse;

	@PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@GetMapping(value = "/access-denied", produces = "application/json")
	public ResponseEntity<AppResponse> accessDeniedHandler() {
		return new ResponseEntity(genericResponse.getAccessDeniedResponse(), HttpStatus.UNAUTHORIZED);
	}

}
