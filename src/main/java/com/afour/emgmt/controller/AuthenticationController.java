/**
 * 
 */
package com.afour.emgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.common.AppResponse;
import com.afour.emgmt.common.AuthRequest;
import com.afour.emgmt.common.GenericResponse;
import com.afour.emgmt.common.RoleEnum;
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
	GenericResponse genericResponse;
	
	@PostMapping(value = "/organizer", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
				return ResponseEntity.ok()
		                .header(
		                        HttpHeaders.AUTHORIZATION,
		                        jwtService.generateToken(authRequest.getUsername())
		                    )
		                    .body(user);
			} else {
				throw new UsernameNotFoundException("invalid user request !");
			}
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping(value = "/visitor", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> authenticateAndGetTokenForVisitor(@RequestBody AuthRequest authRequest) {
		try {
			String username = RoleEnum.VISITOR.name()+authRequest.getUsername();
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
				return ResponseEntity.ok()
		                .header(
		                        HttpHeaders.AUTHORIZATION,
		                        jwtService.generateToken(authRequest.getUsername())
		                    )
		                    .body(user);
			} else {
				throw new UsernameNotFoundException("invalid user request !");
			}
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping(value = "/access-denied", produces = "application/json")
	public ResponseEntity<AppResponse> accessDeniedHandler() {
		return new ResponseEntity(genericResponse.getAccessDeniedResponse(), HttpStatus.UNAUTHORIZED);
	}

}
