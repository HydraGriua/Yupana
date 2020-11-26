package com.acme.yupanaapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.UserService;

@Controller
@RequestMapping("/")
public class RegisterController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private UserService userService;

	@GetMapping("login")
	private String login(Model model) {
		System.err.println("ESTOY EN EL lOGIN");
		Seller seller = new Seller();
		model.addAttribute("usuario", seller);
		return "login";
	}

	@GetMapping
	private String registrarform(Model model) {
		try {
			System.err.println("DENTROOOO");
			Seller seller = new Seller();
			model.addAttribute("usuario", seller);
			//System.out.println(seller.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "index";
	}

	@PostMapping
	private String registrar(@ModelAttribute Seller seller, BindingResult result, Model model) {

		try {

			if (result.hasErrors()) {
				return "redirect:/";
			} else {
				seller.getUser().setDescription("vendedor");
				model.addAttribute("usuario",
						sellerService.createSeller(seller, userService.createUser(seller.getUser()).getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/login";
	}
}
