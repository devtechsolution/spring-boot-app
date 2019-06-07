package org.dev.tech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	
	@RequestMapping("/login")
	//@ResponseBody
	public String loginMessage() {
		
		return "Hello Woorld Using @ComponentScan ";
	}

}
