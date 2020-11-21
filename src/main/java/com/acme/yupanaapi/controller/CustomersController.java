package com.acme.yupanaapi.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.helpers.UserTesting;
import com.acme.yupanaapi.resource.WalletResource;

@Controller
@RequestMapping("mystore/clients")
public class CustomersController {
	@Autowired
	WalletService walletservice;
	
	@GetMapping
	public String viewCustomers(Model model) {
		try {
			System.out.println("Si entro");
			List<Wallet> clientes = walletservice.getAllBySellerId(UserTesting.Users.getIdSeller());
			model.addAttribute("clientes", clientes);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "/mystore/clients";
	}
	
}
