/**
 * 
 */
package com.afour.emgmt.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.afour.emgmt.model.AppResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Configuration
@Slf4j
public class GenericResponseImpl implements GenericResponse {

	@Autowired
	MessageSource messages;

	private String message;

	@Override
	public AppResponse getEmtyRequestResponse() {
		message = messages.getMessage("failed.empty.request.body", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.BAD_REQUEST).build();
	}

	@Override
	public AppResponse getSuccessDataFoundResponse(final Object result, final Integer size) {
		message = messages.getMessage("success.data.found.size", new Object[] { size }, Locale.US);
		log.info(message);
		return AppResponse.builder().message(message).body(result).status(HttpStatus.OK).build();
	}

	@Override
	public AppResponse getNoDataFoundResponse() {
		message = messages.getMessage("no.data.found", null, Locale.US);
		log.warn(message);
		return AppResponse.builder().message(message).status(HttpStatus.NO_CONTENT).build();
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

}
