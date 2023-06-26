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
	public ResponseEntity<List<EsessionDTO>> findSessionEventByID(@PathVariable(value = "eventId") final Integer eventId) {
		List<EsessionDTO> result = service.findSessionEventByID(eventId);
		if (null!=result) {
			log.info(messages.getMessage("success.data.found.size", null, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.warn(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(messages.getMessage("no.data.found", null, null), HttpStatus.NO_CONTENT);
		}
	}

}
