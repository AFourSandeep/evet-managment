/**
 * 
 */
package com.afour.emgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * This is a Controller class User to redirect the root request to the Swagger
 * UI.
 * 
 * 
 * @author Sandeep Jariya
 */
@Controller
@ApiIgnore
public class RedirectController {

	@GetMapping("/")
	public String index() {
		return "redirect:/swagger-ui/index.html";
	}

}
