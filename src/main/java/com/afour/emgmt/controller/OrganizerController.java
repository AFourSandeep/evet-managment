/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

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
import com.afour.emgmt.common.AppResponseBuilder;
import com.afour.emgmt.exception.EmptyRequestException;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.service.OrganizerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is a Controller class having all the REST (GET POST PUT DELETE) end
 * points to manage any Organizer.
 * 
 * @author Sandeep Jariya
 */
@RestController
@RequestMapping("/organizer")
@Api(tags = "Manage Organizers")
public class OrganizerController {

	private final OrganizerService service;

	private final AppResponseBuilder responseBuilder;

	public OrganizerController(OrganizerService service, AppResponseBuilder responseBuilder) {
		this.service = service;
		this.responseBuilder = responseBuilder;
	}

	/* Get all the existing organizers without any filter */
	@ApiOperation(value = "Fetch all the organizers without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the organizers!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/organizers", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchAllOrganizers() {
		List<UserDTO> result = service.fetchAllOrganizers();

		return new ResponseEntity<>(responseBuilder.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}

	/* Get one existing organizer using its id */
	@ApiOperation(value = "Fetch one organizer by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findOrganizerByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		UserDTO result = service.findOrganizerByID(id);

		return new ResponseEntity<>(responseBuilder.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Get one existing organizer using its username */
	@ApiOperation(value = "Fetch one organizers by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findOrganizerByUserName(@RequestParam(value = "userName") final String userName) {
		if (null == userName)
			throw new EmptyRequestException();

		UserDTO result = service.findOrganizerByUserName(userName);

		return new ResponseEntity<>(responseBuilder.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Create a new organizer */
	@ApiOperation(value = "Create a new Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> addOrganizer(@RequestBody UserDTO dto) {
		if (null == dto)
			throw new EmptyRequestException();

		UserDTO result = service.addOrganizer(dto);

		AppResponse response = responseBuilder.getRequestSuccessResponse("organizer.create.success", result, HttpStatus.CREATED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* update an existing organizer */
	@ApiOperation(value = "Update an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> updateOrganizer(@RequestBody UserDTO dto) {
		if (null == dto)
			throw new EmptyRequestException();

		UserDTO result = service.updateOrganizer(dto);

		AppResponse response = responseBuilder.getRequestSuccessResponse("organizer.update.success", result, HttpStatus.CREATED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* Delete one organizer using its id */
	@ApiOperation(value = "Delete an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested organizer!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> deleteOrganizer(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		boolean result = service.deleteOrganizerByID(id);

		AppResponse response = responseBuilder.getRequestSuccessResponse("organizer.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
