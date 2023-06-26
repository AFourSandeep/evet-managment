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

import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.service.VisitorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/visitor")
@Api(tags = "Manage Visitors")
@Slf4j
public class VisitorController {

	@Autowired
	VisitorService service;

	@Autowired
	MessageSource messages;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the visitors without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the visitors!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/visitors")
	public ResponseEntity<List<VisitorDTO>> fetchAllVisitors() {
		List<VisitorDTO> result = service.fetchAllVisitors();
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", new Object[] { result.size() }, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/byId/{ID}")
	public ResponseEntity<VisitorDTO> findVisitorByID(@PathVariable(value = "ID") final Integer ID) {
		VisitorDTO result = service.findVisitorByID(ID);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value="/byName/{username}", produces = "application/json")
	public ResponseEntity<VisitorDTO> findVisitorByUserName(@PathVariable(value = "username") final String USERNAME) {
		VisitorDTO result = service.findVisitorByUserName(USERNAME);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> addVisitor(@RequestBody VisitorDTO dto) {
		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		VisitorDTO result = service.addVisitor(dto);

		if (result != null) {
			log.info(messages.getMessage("visitor.create.success", new Integer[] { result.getVisitorId() }, null));
			return new ResponseEntity(result, HttpStatus.CREATED);
		} else {
			log.error(messages.getMessage("visitor.create.fail", new VisitorDTO[] { dto }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update a Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> updateVisitor(@RequestBody VisitorDTO dto) {
		
		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		VisitorDTO result = service.updateVisitor(dto);

		if (result != null) {
			log.info(messages.getMessage("visitor.update.success", new Integer[] { result.getVisitorId() }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("visitor.update.fail", new Integer[] { dto.getVisitorId() }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the vistor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested visitor!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> deleteVisitorByID(@PathVariable(value = "ID") final Integer ID) {
		if (null == ID) {
			log.warn(messages.getMessage("failed.empty.request.body", null, null));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, null),
					HttpStatus.BAD_REQUEST);
		}
		
		Boolean result = service.deleteVisitorByID(ID);
		
		if (result) {
			log.info(messages.getMessage("visitor.delete.success", new Integer[] { ID }, null));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("visitor.delete.fail", new Integer[] { ID }, null));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/byEventId/{eventId}")
	public ResponseEntity<List<VisitorDTO>> findVisitorsByEventId(@PathVariable(value = "eventId") final Integer eventId) {
		List<VisitorDTO> result = service.findVisitorsByEventId(eventId);
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}

}
