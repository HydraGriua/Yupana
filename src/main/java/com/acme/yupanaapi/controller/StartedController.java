package com.acme.yupanaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class StartedController {
	@GetMapping
	public String starter() {
		return "index";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@GetMapping("access-denied")
	public String accessDenied() {
		return "access-denied";
	}
}
