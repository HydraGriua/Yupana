package com.acme.yupanaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.WalletService;

@Controller
@RequestMapping("mystore/clients")
public class CustomersController {
	@Autowired
	SellerService sellerService;
	
	@Autowired
	WalletService walletService;
	
	@GetMapping
	public String viewCustomers(Model model) {
		try {
			System.err.println("hola como estas");
			List<Wallet> wallets = walletService.getAllBySellerId(1);
			model.addAttribute("wallets", wallets);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "/mystore/clients";
	}
	
}