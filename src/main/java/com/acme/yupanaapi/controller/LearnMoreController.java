package com.acme.yupanaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mystore/learn_more")
public class LearnMoreController {

	
	@GetMapping
	public String viewLearnMore() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/learn_more";
	}
}
