/**
 * 
 */
package com.afour.emgmt.util;

import org.springframework.http.HttpStatus;

import com.afour.emgmt.model.AppResponse;

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
