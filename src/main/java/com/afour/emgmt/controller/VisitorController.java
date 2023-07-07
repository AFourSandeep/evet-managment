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
import com.afour.emgmt.exception.EmptyRequestException;
import com.afour.emgmt.exception.NoDataFoundException;
import com.afour.emgmt.model.UserDTO;
import com.afour.emgmt.model.UserRegistrationDTO;
import com.afour.emgmt.service.VisitorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is a Controller class having all the REST (GET POST PUT DELETE) end
 * points to manage any Visitor.
 * 
 * @author Sandeep Jariya
 */
@RestController
@RequestMapping("/visitor")
@Api(tags = "Manage Visitors")
public class VisitorController {

	@Autowired
	VisitorService service;

	@Autowired
	MessageSource messages;

	@Autowired
	GenericResponse genericResponse;

	private AppResponse response;

	/* Get all the visitors without any filter */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the visitors without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the visitors!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/visitors", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> fetchAllVisitors() throws NoDataFoundException, Exception {
		List<UserDTO> result = service.fetchAllVisitors();

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}

	/* Get a visitor using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findVisitorByID(@PathVariable(value = "id") final Integer id)
			throws NoDataFoundException, Exception {
		if (null == id)
			throw new EmptyRequestException();

		UserDTO result = service.findVisitorByID(id);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Get a visitor using its username */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> findVisitorByUserName(@RequestParam(value = "userName") final String userName)
			throws NoDataFoundException, Exception {
		if (null == userName)
			throw new EmptyRequestException();

		UserDTO result = service.findVisitorByUserName(userName);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Create a new visitor */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR')")
	public ResponseEntity<AppResponse> addVisitor(@RequestBody UserDTO dto) throws Exception {
		if (null == dto)
			throw new EmptyRequestException();

		UserDTO result = service.addVisitor(dto);

		response = genericResponse.getRequestSuccessResponse("visitor.create.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	/* Update an existing visitor */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update a Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR')")
	public ResponseEntity<AppResponse> updateVisitor(@RequestBody UserDTO dto) throws NoDataFoundException, Exception {
		if (null == dto)
			throw new EmptyRequestException();

		UserDTO result = service.updateVisitor(dto);

		response = genericResponse.getRequestSuccessResponse("visitor.update.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	/* Delete an existing visitor using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the vistor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested visitor!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR')")
	public ResponseEntity<AppResponse> deleteVisitorByID(@PathVariable(value = "id") final Integer id)
			throws NoDataFoundException, Exception {
		if (null == id)
			throw new EmptyRequestException();

		Boolean result = service.deleteVisitorByID(id);

		response = genericResponse.getRequestSuccessResponse("visitor.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	/* Register one existing visitor for one or more events */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Register a Visitor for Events.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Registered!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/registerForEvent", consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('VISITOR') or hasAuthority('ORGANIZER')")
	public ResponseEntity<AppResponse> registerVisitorForEvent(@RequestBody UserRegistrationDTO dto)
			throws NoDataFoundException, Exception {
		if (null == dto)
			throw new EmptyRequestException();

		UserDTO result = service.registerVisitorForEvent(dto);
		response = genericResponse.getRequestSuccessResponse("visitor.register.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
