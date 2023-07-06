/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
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

	@Autowired
	EventService service;

	@Autowired
	MessageSource messages;

	@Autowired
	GenericResponse genericResponse;

	private AppResponse response;
	
	/* Get all the existing events without any filter */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the events without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/events", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchAllEvents() {
		List<EventDTO> result = service.fetchAllEvents();

		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}
	
	/* Get all the existing events by filtering them on there status */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the OPEN evets!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the OPEN/CLOSED events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchEventsByStatus(
			@RequestParam(value = "open", defaultValue = "true") final Boolean status) {
		if (null == status)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		List<EventDTO> result = service.fetchEventsByStatus(status);
		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}
	
	/* Get one existing event using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch an Event by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Event!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findEventByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		EventDTO result = service.findEventByID(id);
		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}
	
	/* Create a new event */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Event.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> addEvent(@RequestBody EventDTO eventDTO) {
		if (null == eventDTO)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		EventDTO result = service.addEvent(eventDTO);
		if (result == null)
			return new ResponseEntity(
					genericResponse.getRequestFailResponse("event.create.fail", new EventDTO[] { eventDTO }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("event.create.success", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	/* update one existing event */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update an Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> updateEvent(@RequestBody EventDTO eventDTO) {
		if (null == eventDTO)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		EventDTO result = service.updateEvent(eventDTO);
		if (result == null)
			return new ResponseEntity(
					genericResponse.getRequestFailResponse("event.update.fail", new EventDTO[] { eventDTO }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("event.update.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	/* delete one existing event */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested event!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{id}",  produces = "application/json")
	@PreAuthorize("hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> deleteEventByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		Boolean result = service.deleteEventByID(id);
		if (result == null)
			return new ResponseEntity(genericResponse.getRequestFailResponse("event.delete.fail", new Integer[] { id }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("event.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
