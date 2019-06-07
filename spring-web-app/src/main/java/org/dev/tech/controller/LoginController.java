package org.dev.tech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
public class LoginController {
	
	
	@RequestMapping("/login")
	//@ResponseBody
	public String loginMessage() {
		
		return "login";
	}

}
