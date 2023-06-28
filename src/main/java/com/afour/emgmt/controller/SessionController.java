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

import com.afour.emgmt.model.EsessionDTO;
import com.afour.emgmt.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/session")
@Api(tags = "Manage Session of An Event")
@Slf4j
public class SessionController {

	@Autowired
	SessionService service;

	@Autowired
	MessageSource messages;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all Session by its Event Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the sessions!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/byEventId/{eventId}")
	public ResponseEntity<List<EsessionDTO>> findSessionEventByID(
			@PathVariable(value = "eventId") final Integer eventId) {
		List<EsessionDTO> result = service.findSessionEventByID(eventId);
		if (null != result) {
			log.info(messages.getMessage("success.data.found.size", null, Locale.US));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, Locale.US));
			return new ResponseEntity(messages.getMessage("no.data.found", null, Locale.US), HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch Session by Session Id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found the sessions!"),
			@ApiResponse(code = 204, message = "No data found!") })
	@GetMapping("/byId/{ID}")
	public ResponseEntity<EsessionDTO> findSessionByID(@PathVariable(value = "ID") final Integer ID) {
		EsessionDTO result = service.findSessionByID(ID);
		if (null != result) {
			log.info(messages.getMessage("success.data.found.size", null, Locale.US));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, Locale.US));
			return new ResponseEntity(messages.getMessage("no.data.found", null, Locale.US), HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Create a new Session.")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EsessionDTO> addSession(@RequestBody EsessionDTO dto) {
		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, Locale.US));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, Locale.US),
					HttpStatus.BAD_REQUEST);
		}

		EsessionDTO result = service.addSession(dto);

		if (result != null) {
			log.info(
					messages.getMessage("session.create.success", new Integer[] { result.getEsessionId() }, Locale.US));
			return new ResponseEntity(result, HttpStatus.CREATED);
		} else {
			log.error(messages.getMessage("session.create.fail", new EsessionDTO[] { dto }, Locale.US));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Update the Session.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted and Updated!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EsessionDTO> updateSession(@RequestBody EsessionDTO dto) {

		if (null == dto) {
			log.warn(messages.getMessage("failed.empty.request.body", null, Locale.US));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, Locale.US),
					HttpStatus.BAD_REQUEST);
		}

		EsessionDTO result = service.updateSession(dto);

		if (result != null) {
			log.info(
					messages.getMessage("session.update.success", new Integer[] { result.getEsessionId() }, Locale.US));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("session.update.fail", new Integer[] { dto.getEsessionId() }, Locale.US));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Delete the Session.")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted the requested session!"),
			@ApiResponse(code = 400, message = "Bad Request!") })
	@DeleteMapping(value = "/deleteById/{ID}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<EsessionDTO> deleteSession(@PathVariable(value = "ID") final Integer ID) {
		if (null == ID) {
			log.warn(messages.getMessage("failed.empty.request.body", null, Locale.US));
			return new ResponseEntity(messages.getMessage("failed.empty.request.body", null, Locale.US),
					HttpStatus.BAD_REQUEST);
		}

		Boolean result = service.deleteSessionByID(ID);

		if (result) {
			log.info(messages.getMessage("session.delete.success", new Integer[] { ID }, Locale.US));
			return new ResponseEntity(result, HttpStatus.ACCEPTED);
		} else {
			log.error(messages.getMessage("session.delete.fail", new Integer[] { ID }, Locale.US));
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
