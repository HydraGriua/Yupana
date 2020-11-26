package com.acme.yupanaapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.helpers.UserTesting;
import com.acme.yupanaapi.resource.UserWalletResource;

@Controller
@RequestMapping("mystore")
public class CustomersController {
	@Autowired
	SellerService sellerService;

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	WalletService walletService;

	@Autowired
	FlowService flowService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@GetMapping("/clients")
	public String viewCustomers(Authentication auth, HttpSession session, Model model) {
		try {
			
			String username =  auth.getName();
			System.err.print("\n username = " + username);
			int id = 0;
			Seller seller= sellerService.getSellerByUsername(username);
			id= seller.getId();
			seller.setPassword(null);
			if(session.getAttribute("usuario") == null) {	
				session.setAttribute("usuario", seller);
				System.out.println(seller.getId());
			}
			
			List<Wallet> wallets = walletService.getAllBySellerId(id);
			List<UserWalletResource> walletsList = new ArrayList<>();
            if (wallets.size()>0) {
                UserWalletResource walletT = new UserWalletResource();
                for(Wallet x : wallets) {
                	System.err.print("\n"+x);
                	UserWalletResource infoWallet = flowService.getData(x.getId());
                	if(infoWallet!= null) {
                		System.err.println(infoWallet.getWalletId());
                		walletsList.add(infoWallet);
                	}
                }
            }
			model.addAttribute("wallets", walletsList);
			model.addAttribute("idSession", id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/clients";
	}

	@GetMapping("/client-details")
	public String viewLearnMore(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			System.out.println("Id del cliente" + " " + id);
			Wallet walletT = walletService.getWalletById(id);
			Flow flowT = flowService.getLastFlow(walletT.getId());
			List<Transaction> transacionesT = transactionService.getAllByFlowId(flowT.getId());
			List<Subscription> subscripcionesT = subscriptionService.getAllByWalletId(walletT.getId());
			if (transacionesT != null) {
				model.addAttribute("wallets", walletT);
				model.addAttribute("flows", flowT);
				model.addAttribute("transacionesT", transacionesT);
				model.addAttribute("deliveries", subscripcionesT);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/client-details";
	}
	@GetMapping("/profile")
	public String viewProfile() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/profile";
	}

	@GetMapping("/learn_more")
	public String viewLearnMore() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/mystore/learn_more";
	}
}