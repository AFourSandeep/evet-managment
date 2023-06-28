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
import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.service.OrganizerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/organizer")
@Api(tags = "Manage Organizers")
@Slf4j
public class OrganizerController {

	@Autowired
	OrganizerService service;

	@Autowired
	MessageSource messages;

	
	private AppResponse response;

	private String message;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the organizers without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the organizers!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/organizers", produces = "application/json")
	public ResponseEntity<AppResponse> fetchAllOrganizers() {
		List<OrganizerDTO> result = service.fetchAllOrganizers();
		response = new AppResponse();
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
	@ApiOperation(value = "Fetch one organizer by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/byId/{ID}", produces = "application/json")
	public ResponseEntity<AppResponse> findOrganizerByID(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		OrganizerDTO result = service.findOrganizerByID(ID);
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
	@ApiOperation(value = "Fetch one organizers by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/byName/{username}", produces = "application/json")
	public ResponseEntity<AppResponse> findOrganizerByUserName(
			@PathVariable(value = "username") final String USERNAME) {
		response = new AppResponse();
		if (null == USERNAME) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		OrganizerDTO result = service.findOrganizerByUserName(USERNAME);
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
	@ApiOperation(value = "Create a new Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> addOrganizer(@RequestBody OrganizerDTO dto) {
		response = new AppResponse();
		if (null == dto) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.NO_CONTENT);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		OrganizerDTO result = service.addOrganizer(dto);
		if (result != null) {
			message = messages.getMessage("organizer.create.success", new Integer[] { result.getOrganizerId() },
					Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.CREATED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("organizer.create.fail", new OrganizerDTO[] { dto }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> updateOrganizer(@RequestBody OrganizerDTO dto) {
		response = new AppResponse();
		if (null == dto) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		OrganizerDTO result = service.updateOrganizer(dto);
		if (result != null) {
			message = messages.getMessage("organizer.update.success", new Integer[] { result.getOrganizerId() },
					Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("organizer.update.fail", new Integer[] { dto.getOrganizerId() }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested organizer!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> deleteOrganizer(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Boolean result = service.deleteOrganizerByID(ID);
		if (result) {
			message = messages.getMessage("organizer.delete.success", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("organizer.delete.fail", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}

}
