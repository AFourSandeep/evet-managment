/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.common.AppResponse;
import com.afour.emgmt.common.GenericResponse;
import com.afour.emgmt.exception.EmptyRequestException;
import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is a Controller class having all the REST (GET POST PUT DELETE) end
 * points to manage any Session.
 * 
 * @author Sandeep Jariya
 */
@RestController
@RequestMapping("/session")
@Api(tags = "Manage Sessions")
public class SessionController {

	private final SessionService service;

	private final GenericResponse genericResponse;

	public SessionController(SessionService service, GenericResponse genericResponse) {
		this.service = service;
		this.genericResponse = genericResponse;
	}

	/* Get all sessions of an event */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all Session by its Event Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the sessions!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findSessionEventByID(@RequestParam(value = "eventId") final Integer eventId) {
		if (null == eventId)
			throw new EmptyRequestException();

		List<EsessionDTO> result = service.findSessionEventByID(eventId);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}

	/* Get a existing session using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch Session by Session Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the sessions!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findSessionByID(@PathVariable(value = "ID") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		EsessionDTO result = service.findSessionByID(id);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Create a new session under any event */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Session.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> addSession(@RequestBody EsessionDTO dto) {
		if (null == dto)
			throw new EmptyRequestException();

		EsessionDTO result = service.addSession(dto);

		AppResponse response = genericResponse.getRequestSuccessResponse("session.create.success", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	/* Update an existing session */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update the Session.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> updateSession(@RequestBody EsessionDTO dto) {
		if (null == dto)
			throw new EmptyRequestException();

		EsessionDTO result = service.updateSession(dto);

		AppResponse response = genericResponse.getRequestSuccessResponse("session.update.success", result, HttpStatus.CREATED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* Delete one existing session */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the Session.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested session!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> deleteSession(@PathVariable(value = "ID") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		Boolean result = service.deleteSessionByID(id);

		AppResponse response = genericResponse.getRequestSuccessResponse("session.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
