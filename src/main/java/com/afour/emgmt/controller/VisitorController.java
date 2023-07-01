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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afour.emgmt.model.AppResponse;
import com.afour.emgmt.model.VisitorDTO;
import com.afour.emgmt.model.VisitorRegistrationDTO;
import com.afour.emgmt.service.VisitorService;
import com.afour.emgmt.util.GenericResponse;

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
	
	/* Get all the visitors without any filter*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the visitors without any filter!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found all the visitors!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/visitors", produces = "application/json")
	public ResponseEntity<AppResponse> fetchAllVisitors() {
		List<VisitorDTO> result = service.fetchAllVisitors();
		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, result.size()), HttpStatus.OK);
	}
	
	/* Get a visitor using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by ID!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<AppResponse> findVisitorByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		VisitorDTO result = service.findVisitorByID(id);
		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}
	
	/* Get a visitor using its username */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch one Visitor by USERNAME!")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the Visitor!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<AppResponse> findVisitorByUserName(@RequestParam(value = "userName") final String userName) {
		if (null == userName)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		VisitorDTO result = service.findVisitorByUserName(userName);
		if (result == null)
			return new ResponseEntity(genericResponse.getNoDataFoundResponse(), HttpStatus.OK);

		return new ResponseEntity(genericResponse.getSuccessDataFoundResponse(result, 1), HttpStatus.OK);
	}

	/* Create a new visitor */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> addVisitor(@RequestBody VisitorDTO dto) {
		if (null == dto)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		VisitorDTO result = service.addVisitor(dto);
		if (result == null)
			return new ResponseEntity(
					genericResponse.getRequestFailResponse("visitor.create.fail", new VisitorDTO[] { dto }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("visitor.create.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	/* Update an existing visitor */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update a Visitor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> updateVisitor(@RequestBody VisitorDTO dto) {
		if (null == dto)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		VisitorDTO result = service.updateVisitor(dto);
		if (result == null)
			return new ResponseEntity(
					genericResponse.getRequestFailResponse("visitor.update.fail", new Integer[] { dto.getVisitorId() }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("visitor.update.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	/* Delete an existing visitor using its id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the vistor.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested visitor!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> deleteVisitorByID(@PathVariable(value = "id") final Integer id) {
		if (null == id)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		Boolean result = service.deleteVisitorByID(id);
		if (result == null)
			return new ResponseEntity(
					genericResponse.getRequestFailResponse("visitor.delete.fail", new Integer[] { id }),
					HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("visitor.delete.success", result, HttpStatus.ACCEPTED);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	/* Register one existing visitor for one or more events */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Register a Visitor for Events.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Registered!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/registerForEvent", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppResponse> registerVisitorForEvent(@RequestBody VisitorRegistrationDTO dto) {
		if (null == dto)
			return new ResponseEntity(genericResponse.getEmtyRequestResponse(), HttpStatus.BAD_REQUEST);

		VisitorDTO result = service.registerVisitorForEvent(dto);
		if (result == null)
			return new ResponseEntity(genericResponse.getRequestFailResponse("visitor.register.fail",
					new Integer[] { dto.getVisitorId() }), HttpStatus.BAD_REQUEST);

		response = genericResponse.getRequestSuccessResponse("visitor.register.successs", result, HttpStatus.CREATED);
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
