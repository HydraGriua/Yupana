package com.acme.yupanaapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.yupanaapi.domain.model.Flow;
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
	public String viewCustomers(@RequestParam(name = "id", required = false) int idSeller, Model model) {
		try {
			System.err.println("hola como estas");
			List<Wallet> wallets = walletService.getAllBySellerId(UserTesting.Users.getIdSeller());
			List<UserWalletResource> walletsT = new ArrayList<>();
			if (wallets.size()>0) {
				System.err.println("DENTROOOOO");
				System.err.println("SIZE" + wallets.size());
				for (Wallet x : wallets) {
					System.err.print(wallets.get(0));
					System.out.println("\n ID_____"+x.getId());
					UserWalletResource resource = flowService.getData(x.getId());
					System.err.println("\n"+resource.getState());
					
					List<Subscription> lista = subscriptionService.getAllByWalletId(x.getId());
					System.err.println("\n LISTA !.:"+lista.get(0));
					System.err.println("\n LISTA !.:"+lista.get(1));
					if (lista.size() > 0) {
						System.err.println("DENTROOOOODENTROLLLLLLLLLLL");
							for(Subscription z : lista) {
								System.err.println(z);
							}
							Subscription subscription = lista.get(lista.size() - 1);
							if(subscription != null) {
								System.err.println("A MIMIRRRRRRRRRRRRRRRRRRRRRRRRRRRRR°!!!!!!!!!!!!!!!!");
								System.err.println(subscription);
								resource.setSubscriptionId(subscription.getId());
								resource.setSAmount(subscription.getAmount());
								resource.setSCreationDate(subscription.getCreationDate());
								resource.setSExpirationDate(subscription.getExpirationDate());
								resource.setSType(subscription.getSubscriptionType());
								walletsT.add(resource);
							}
					}
					System.out.println("Id" + walletsT.get(0).getFlowId());
				}
			}
			/// List<Wallet> wallets = walletService.getAllBySellerId(1);
			model.addAttribute("wallets", walletsT);
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

}