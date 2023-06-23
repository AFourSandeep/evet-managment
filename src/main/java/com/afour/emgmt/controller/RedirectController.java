/**
 * 
 */
package com.afour.emgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 */
@Controller
@ApiIgnore
public class RedirectController {
	
	@GetMapping("/")
	public String index(){
		return "redirect:/swagger-ui/index.html";
	}

	
}
