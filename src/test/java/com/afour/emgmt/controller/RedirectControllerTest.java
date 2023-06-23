package com.afour.emgmt.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RedirectControllerTest {
	
	@InjectMocks
	RedirectController rController;

	@Test
	void test_index() {
		//given
		
		//when
		var result = rController.index();
		
		//then
		assertNotNull(result);
		assertTrue(result.contains("swagger"));
	}

}
