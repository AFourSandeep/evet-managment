/**
 * 
 */
package com.afour.emgmt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.afour.emgmt.model.OrganizerDTO;
import com.afour.emgmt.service.OrganizerService;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class OrganizerControllerTest {
	
	@Mock
	OrganizerService orgService;
	
	@Mock
	MessageSource messages;
	
	@InjectMocks
	OrganizerController orgController ;
	
	@Test
	void fetchAllOrganizers_for_data() {
		//given
		List<OrganizerDTO> dtos = List.of(new OrganizerDTO(), new OrganizerDTO());
		when(orgService.fetchAllOrganizers()).thenReturn(dtos);
		
		//when
		var result= orgController.fetchAllOrganizers();
		
		//then
		assertEquals(HttpStatus.OK,result.getStatusCode());
		assertEquals(dtos.size(), result.getBody().size());
	}

	@Test
	void fetchAllOrganizers_for_no_data() {
		//given
		when(orgService.fetchAllOrganizers()).thenReturn(null);
		
		//when
		var result= orgController.fetchAllOrganizers();
		
		//then
		assertEquals(HttpStatus.NO_CONTENT,result.getStatusCode());
	}

}
