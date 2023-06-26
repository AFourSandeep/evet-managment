/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.model.EventDTO;
import com.afour.emgmt.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/event")
@Api(tags = "Manage Events")
@Slf4j
public class EventController {
	
	@Autowired
	EventService service;

	@Autowired
	MessageSource messages;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the events without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/events")
	public ResponseEntity<List<EventDTO>> fetchAllEvents() {
		List<EventDTO> result = service.fetchAllEvents();
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", new Object[] { result.size() }, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the OPEN evets!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the OPEN/Active events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/openEvents")
	public ResponseEntity<List<EventDTO>> fetchAllOpenEvents() {
		List<EventDTO> result = service.fetchAllOpenEvents();
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", new Object[] { result.size() }, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch an Event by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Event!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/byId/{ID}")
	public ResponseEntity<EventDTO> findEventByID(@PathVariable(value = "ID") final Integer ID) {
		EventDTO result = service.findEventByID(ID);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Event.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> addVisitor(@RequestBody EventDTO dto) {
		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		EventDTO result = service.addEvent(dto);

		if (result != null) {
			log.info(messages.getMessage("event.create.success", new Integer[] { result.getEventId() }, null));
			return new ResponseEntity(result, HttpStatus.CREATED);
		} else {
			log.error(messages.getMessage("event.create.fail", new EventDTO[] { dto }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update an Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> updateVisitor(@RequestBody EventDTO dto) {
		
		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		EventDTO result = service.updateEvent(dto);

		if (result != null) {
			log.info(messages.getMessage("event.update.success", new Integer[] { result.getEventId() }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("event.update.fail", new Integer[] { dto.getEventId() }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested event!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> deleteEventByID(@PathVariable(value = "ID") final Integer ID) {
		if (null == ID) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		Boolean result = service.deleteEventByID(ID);
		
		if (result) {
			log.info(messages.getMessage("event.delete.success", new Integer[] { ID }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("event.delete.fail", new Integer[] { ID }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
