/**
 * 
 */
package com.afour.emgmt.common;

import org.springframework.http.HttpStatus;

/**
 * 
 */
public interface GenericResponse {

	AppResponse getEmtyRequestResponse();

	AppResponse getSuccessDataFoundResponse(Object result, Integer size);

	AppResponse getNoDataFoundResponse();

	AppResponse getRequestFailResponse(String string, Object[] objects);

	AppResponse getRequestSuccessResponse(String string, Object result, HttpStatus status);

	AppResponse getAccessDeniedResponse();

}
