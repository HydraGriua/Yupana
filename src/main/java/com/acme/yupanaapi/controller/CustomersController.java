package com.acme.yupanaapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.resource.UserWalletResource;

@Controller
@RequestMapping("mystore/clients")
public class CustomersController {
	@Autowired
	SellerService sellerService;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	FlowService flowService;
	
	@GetMapping
	public String viewCustomers(Model model) {
		try {
			System.err.println("hola como estas");
			
			List<Wallet> wallets =  walletService.getAllBySellerId(1);
            List<UserWalletResource>  walletsT = new ArrayList<>();
          
            for (Wallet x : wallets) {
               System.err.println(x.getId());
               UserWalletResource resource = flowService.getData(x.getId());
               List<Subscription> lista = subscriptionService.getAllByWalletId(x.getId());
               if(lista.size()>=1) {
            	   Subscription subscription = lista.get(lista.size()-1);
                   resource.setSubscriptionId(subscription.getId());
                   resource.setSAmount(subscription.getAmount());
                   resource.setSCreationDate(subscription.getCreationDate());
                   resource.setSExpirationDate(subscription.getExpirationDate());
                   resource.setSType(subscription.getSubscriptionType());
                   walletsT.add(resource);
               }
                       	
            	System.err.println(walletsT.get(0).getBalance() + " "+ 
            	walletsT.get(0).getSExpirationDate() + " "  + 
            	walletsT.get(0).getCreditLine()+" " + walletsT.get(0).getTotalDebt() + " " +  walletsT.get(0).getSAmount() 
            	+ " " + walletsT.get(0).getType() + " " + walletsT.get(0).getName());
               
            }
	
			///List<Wallet> wallets = walletService.getAllBySellerId(1);
			model.addAttribute("wallets", walletsT);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return "/mystore/clients";
	}
	
}