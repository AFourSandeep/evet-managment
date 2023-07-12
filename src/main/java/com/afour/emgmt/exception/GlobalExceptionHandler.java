/**
 * 
 */
package com.afour.emgmt.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.afour.emgmt.common.AppResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	MessageSource messages;

	private String message;
	
	//To handle the unexpected termination
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AppResponse handleException(Exception e) {
		message = messages.getMessage("exception.occured", null, Locale.US);
		log.error(message);
		log.error(e.getMessage());
		return AppResponse.builder().message(message).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@ExceptionHandler(EmptyRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AppResponse handleEmptyRequestException() {
		message = messages.getMessage("failed.empty.request.body", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(NoDataFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public AppResponse handleNoDataFoundException() {
		message = messages.getMessage("no.data.found", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public AppResponse handleUserAlreadyExistException() {
		message = messages.getMessage("user.already.exists", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@ExceptionHandler(UndefinedRoleException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public AppResponse handleUndefinedRoleException() {
		message = messages.getMessage("role.not.exist", null, Locale.US);
		log.error(message);
		return AppResponse.builder().message(message).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
