/**
 * 
 */
package com.afour.emgmt.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.afour.emgmt.common.AppResponse;

/**
 * 
 */
public class TokenExpiredException extends RuntimeException implements AuthenticationEntryPoint {
	
	String token;

	private static final long serialVersionUID = 1L;
	
	private final HttpMessageConverter<String> messageConverter;

    public TokenExpiredException(String token) {
        this.messageConverter = new StringHttpMessageConverter();
        this.token = token;
    }

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		AppResponse apiError = AppResponse.builder().message("Token expired please get a new token.")
	    		.body("Expired Token: "+ token).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        messageConverter.write(apiError.toString(), MediaType.APPLICATION_JSON, outputMessage);
	}

}
