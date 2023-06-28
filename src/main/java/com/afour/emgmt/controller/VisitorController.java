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
import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.model.VisitorRegistrationDTO;
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
	
	private AppResponse response;

	private String message;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the visitors without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the visitors!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value="/visitors", produces = "application/json")
	public ResponseEntity<List<VisitorDTO>> fetchAllVisitors() {
		response = new AppResponse();
		List<VisitorDTO> result = service.fetchAllVisitors();
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
	@ApiOperation(value = "Fetch one Visitor by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value ="/byId/{ID}", produces = "application/json")
	public ResponseEntity<VisitorDTO> findVisitorByID(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		VisitorDTO result = service.findVisitorByID(ID);
		if (result != null) {
			message = messages.getMessage("success.data.found.size", new Object[] { new Integer[] { 1 } }, Locale.US);
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
	@ApiOperation(value = "Fetch one Visitor by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value="/byName/{username}", produces = "application/json")
	public ResponseEntity<VisitorDTO> findVisitorByUserName(@PathVariable(value = "username") final String USERNAME) {
		response = new AppResponse();
		if (null == USERNAME) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		VisitorDTO result = service.findVisitorByUserName(USERNAME);
		if (result != null) {
			message = messages.getMessage("success.data.found.size", new Object[] { new Integer[] { 1 } }, Locale.US);
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
	@ApiOperation(value = "Create a new Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> addVisitor(@RequestBody VisitorDTO dto) {
		response = new AppResponse();
		if (null == dto) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		VisitorDTO result = service.addVisitor(dto);
		if (result != null) {
			message = messages.getMessage("visitor.create.success", new Object[] { new Integer[] { result.getVisitorId() } }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.CREATED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("visitor.create.fail", new VisitorDTO[] { dto }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update a Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> updateVisitor(@RequestBody VisitorDTO dto) {
		response = new AppResponse();
		if (null == dto) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		VisitorDTO result = service.updateVisitor(dto);
		if (result != null) {
			message = messages.getMessage("visitor.update.success", new Integer[] { result.getVisitorId() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("visitor.update.fail", new Integer[] { dto.getVisitorId() }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the vistor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested visitor!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> deleteVisitorByID(@PathVariable(value = "ID") final Integer ID) {
		response = new AppResponse();
		if (null == ID) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		Boolean result = service.deleteVisitorByID(ID);
		if (result) {
			message = messages.getMessage("visitor.delete.success", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.ACCEPTED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("visitor.delete.fail", new Integer[] { ID }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Register a Visitor for Events.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Registered!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/registerForEvent", consumes = "application/json", produces = "application/json")
	public ResponseEntity<VisitorDTO> registerVisitorForEvent(@RequestBody VisitorRegistrationDTO dto) {
		response = new AppResponse();
		if (null == dto) {
			message = messages.getMessage("failed.empty.request.body", null, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.error(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
		
		VisitorDTO result = service.registerVisitorForEvent(dto);
		if (result != null) {
			message = messages.getMessage("visitor.register.success", new Object[] { result.getVisitorId() }, Locale.US);
			response.setMessage(message);
			response.setBody(result);
			response.setStatus(HttpStatus.CREATED);
			log.info(message);
			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			message = messages.getMessage("visitor.register.fail", new Object[] { dto }, Locale.US);
			response.setMessage(message);
			response.setStatus(HttpStatus.BAD_REQUEST);
			log.warn(message);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}
	}
	
}
