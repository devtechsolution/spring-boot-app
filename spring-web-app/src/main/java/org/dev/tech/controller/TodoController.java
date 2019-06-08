package org.dev.tech.controller;

import org.dev.tech.service.LoginService;
import org.dev.tech.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@Controller
public class TodoController {

	@Autowired
	TodoService  service;
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodoList(ModelMap model){
		model.put("todos", service.retrieveTodos("devtechsolution"));
		return "list-todos";
	}
	
}
