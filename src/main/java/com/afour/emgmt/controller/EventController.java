/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;
import java.util.Locale;

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

import com.afour.emgmt.model.AppResponse;
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

	AppResponse response;

	private String message;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the events without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/events", produces = "application/json")
	public ResponseEntity<List<EventDTO>> fetchAllEvents() {
		response = new AppResponse();
		List<EventDTO> result = service.fetchAllEvents();
		if (result != null) {
			message = messages.getMessage("success.data.found.size", new Object[] { result.size() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.OK);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("no.data.found", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.NO_CONTENT);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the OPEN evets!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the OPEN/Active events!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/openEvents", produces = "application/json")
	public ResponseEntity<List<EventDTO>> fetchAllOpenEvents() {
		response = new AppResponse();
		List<EventDTO> result = service.fetchAllOpenEvents();
		if (result != null) {
			message = messages.getMessage("success.data.found.size", new Object[] { result.size() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.OK);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("no.data.found", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.NO_CONTENT);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch an Event by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Event!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/byId/{ID}", produces = "application/json")
	public ResponseEntity<EventDTO> findEventByID(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		EventDTO result = service.findEventByID(ID);
		if (result != null) {
			message = messages.getMessage("success.data.found.size", new Integer[] { 1 }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.OK);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("no.data.found", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.NO_CONTENT);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.OK);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Event.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO) {
		response = new AppResponse();
		if (null == eventDTO) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		EventDTO result = service.addEvent(eventDTO);
		if (result != null) {
			message = messages.getMessage("event.create.success", new Integer[] { result.getEventId() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.CREATED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("event.create.fail", new EventDTO[] { eventDTO }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update an Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDTO) {
		response = new AppResponse();
		if (null == eventDTO) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		EventDTO result = service.updateEvent(eventDTO);
		if (result != null) {
			message = messages.getMessage("event.update.success", new Integer[] { result.getEventId() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("event.update.fail", new Integer[] { eventDTO.getEventId() }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the Event.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested event!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EventDTO> deleteEventByID(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Boolean result = service.deleteEventByID(ID);
		if (result) {
			message = messages.getMessage("event.delete.success", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("event.delete.fail", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

}
