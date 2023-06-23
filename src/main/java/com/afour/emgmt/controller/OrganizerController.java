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
@Api(tags="Manage Organizers")
@Slf4j
public class OrganizerController {

	@Autowired
	OrganizerService service;

	@Autowired
	MessageSource messages;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Fetch all the organizers without any filter!")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Found all the organizers!"),
			@ApiResponse(code = 204, message = "No data found!")
	})
	@GetMapping("/organizers")
	public ResponseEntity<List<OrganizerDTO>> fetchAllOrganizars() {
		List<OrganizerDTO> result = service.fetchAllOrganizers();
		if (result != null) {
			log.info(messages.getMessage("success.data.found.size", new Object[] {result.size()}, null));
			return new ResponseEntity(result, HttpStatus.OK);
		} else {
			log.info(messages.getMessage("no.data.found", null, null));
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

}
