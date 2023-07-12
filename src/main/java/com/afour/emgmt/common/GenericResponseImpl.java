/**
 * 
 */
package com.afour.emgmt.common;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Component
@Slf4j
public class GenericResponseImpl implements GenericResponse {

	private final MessageSource messages;

	public GenericResponseImpl(@Qualifier("messageSource") MessageSource messages) {
		this.messages = messages;
	}

	private String message;

	@Override
	public AppResponse getSuccessDataFoundResponse(final Object result, final Integer size) {
		message = messages.getMessage("success.data.found.size", new Object[] { size }, Locale.US);
		log.info(message);
		return AppResponse.builder().message(message).body(result).status(HttpStatus.OK).build();
	}

	@Override
	public AppResponse getRequestFailResponse(String string, Object[] objects) {
		message = messages.getMessage(string, objects, Locale.US);
		log.warn(message);
		return AppResponse.builder().message(message).status(HttpStatus.BAD_REQUEST).build();
	}

	@Override
	public AppResponse getRequestSuccessResponse(String string, Object result, HttpStatus status) {
		message = messages.getMessage(string, null, Locale.US);
		log.info(message);
		return AppResponse.builder().message(message).body(result).status(status).build();
	}

	@Override
	public AppResponse getAccessDeniedResponse() {
		message = messages.getMessage("access.denied.handler", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.UNAUTHORIZED).build();
	}

}
