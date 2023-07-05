/**
 * 
 */
package com.afour.emgmt.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.afour.emgmt.common.AppResponse;

/**
 * 
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private final HttpMessageConverter<String> messageConverter;

    @SuppressWarnings("unused")
	private final ModelMapper mapper;

    public CustomAuthenticationEntryPoint(ModelMapper mapper) {
        this.messageConverter = new StringHttpMessageConverter();
        this.mapper = mapper;
    }

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		AppResponse apiError = new AppResponse();
        apiError.setMessage(authException.getMessage());
        apiError.setStatus(HttpStatus.UNAUTHORIZED);

        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
        messageConverter.write(apiError.toString(), MediaType.APPLICATION_JSON, outputMessage);
	}

}
