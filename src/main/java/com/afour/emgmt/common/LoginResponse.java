/**
 * 
 */
package com.afour.emgmt.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class LoginResponse {

	private HttpStatus status;
	private String username;
	private String message;
	private String token;
	private List<GrantedAuthority> authorities;

}
