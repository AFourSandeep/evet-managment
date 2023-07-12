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
import com.afour.emgmt.common.GenericResponse;
import com.afour.emgmt.exception.EmptyRequestException;
import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is a Controller class having all the REST (GET POST PUT DELETE) end
 * points to manage any Event.
 * 
 * @author Sandeep Jariya
 */
@RestController
@RequestMapping("/event")
@Api(tags = "Manage Events")
public class EventController {

	private final EventService service;

	private final GenericResponse genericResponse;

	public EventController(EventService service, GenericResponse genericResponse) {
		this.service = service;
		this.genericResponse = genericResponse;
	}

	/* Get all the existing events without any filter */
	@ApiOperation(value = "Fetch all the events without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchAllEvents() {
		List<EventDTO> result = service.fetchAllEvents();

		return new ResponseEntity<>(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}

	/* Get all the existing events by filtering them on there status */
	@ApiOperation(value = "Fetch all the OPEN evets!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the OPEN/CLOSED events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchEventsByStatus(
			@RequestParam(value = "open", defaultValue = "true") final Boolean status) {
		if (null == status)
			throw new EmptyRequestException();

		List<EventDTO> result = service.fetchEventsByStatus(status);

		return new ResponseEntity<>(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}

	/* Get one existing event using its id */
	@ApiOperation(value = "Fetch an Event by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Event!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findEventByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		EventDTO result = service.findEventByID(id);

		return new ResponseEntity<>(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Create a new event */
	@ApiOperation(value = "Create a new Event.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> addEvent(@RequestBody EventDTO eventDTO) {
		if (null == eventDTO)
			throw new EmptyRequestException();

		EventDTO result = service.addEvent(eventDTO);

		AppResponse response = genericResponse.getRequestSuccessResponse("event.create.success", result, HttpStatus.CREATED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* update one existing event */
	@ApiOperation(value = "Update an Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> updateEvent(@RequestBody EventDTO eventDTO) {
		if (null == eventDTO)
			throw new EmptyRequestException();

		EventDTO result = service.updateEvent(eventDTO);

		AppResponse response = genericResponse.getRequestSuccessResponse("event.update.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* delete one existing event */
	@ApiOperation(value = "Delete the Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested event!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> deleteEventByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			throw new EmptyRequestException();

		boolean result = service.deleteEventByID(id);

		AppResponse response = genericResponse.getRequestSuccessResponse("event.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
