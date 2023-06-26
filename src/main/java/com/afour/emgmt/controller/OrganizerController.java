/**
 * 
 */
package com.afour.emgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the organizers without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the organizers!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/organizers")
	public ResponseEntity<List<OrganizerDTO>> fetchAllOrganizers() {
		List<OrganizerDTO> result = service.fetchAllOrganizers();
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", new Object[] { result.size() }, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one organizer by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/findByID/{ID}")
	public ResponseEntity<List<OrganizerDTO>> findOrganizerByID(@PathVariable(value = "ID") final Integer ID) {
		OrganizerDTO result = service.findOrganizerByID(ID);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one organizers by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the organizer!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/finaByName/{username}")
	public ResponseEntity<List<OrganizerDTO>> findOrganizerByUserName(@PathVariable(value = "username") final String USERNAME) {
		OrganizerDTO result = service.findOrganizerByUserName(USERNAME);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<OrganizerDTO> addAnOrganizer(@RequestBody OrganizerDTO orgDTO) {
		if (null == orgDTO) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		OrganizerDTO result = service.addAnOrganizer(orgDTO);

		if (result != null) {
			log.info(messages.getMessage("organizer.create.success", new Integer[] { result.getOrganizerId() }, null));
			return new ResponseEntity(result, HttpStatus.CREATED);
		} else {
			log.error(messages.getMessage("organizer.create.fail", new OrganizerDTO[] { orgDTO }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<OrganizerDTO> updateAnOrganizer(@RequestBody OrganizerDTO orgDTO) {
		
		if (null == orgDTO) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		OrganizerDTO result = service.updateAnOrganizer(orgDTO);

		if (result != null) {
			log.info(messages.getMessage("organizer.update.success", new Integer[] { result.getOrganizerId() }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("organizer.update.fail", new Integer[] { orgDTO.getOrganizerId() }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete an Organizer.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested organizer!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<OrganizerDTO> deleteAnOrganizer(@PathVariable(value = "ID") final Integer ID) {
		if (null == ID) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		Boolean result = service.deleteAnOrganizerByID(ID);
		
		if (result) {
			log.info(messages.getMessage("organizer.delete.success", new Integer[] { ID }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("organizer.delete.fail", new Integer[] { ID }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
