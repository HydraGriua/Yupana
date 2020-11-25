package com.acme.yupanaapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.HistorialService;
import com.acme.yupanaapi.domain.service.SaleService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.resource.InfoFilter;
import com.acme.yupanaapi.resource.SaveSellerResource;
import com.acme.yupanaapi.resource.SellerResource;
import com.acme.yupanaapi.resource.UserWalletResource;
import com.acme.yupanaapi.resource.fullInfoResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@CrossOrigin
@RequestMapping("mystore")
public class SellersController {
	// Mapeador de entidad con resource
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private SellerService sellerService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private HistorialService historialService;
	@Autowired
	private SaleService saleService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private SubscriptionService subscriptionService;
	////////////////////////////////////////////////////////////
	// Metodos crud para las llamadas

	@GetMapping("/records")
	public String getSellerById(Model model) {
		try {
			InfoFilter infoFilter = new InfoFilter();
			List<Transaction> t = new ArrayList<>();
			historialService.getAllHistorialBySellerId(1);
			for (Historial x : historialService.getAllHistorialBySellerId(1)) {
				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
					t.add(transacT);
				}
			}
			model.addAttribute("infoFilter", infoFilter);
			model.addAttribute("sellerInfo", sellerService.getSellerById(1));
			model.addAttribute("transactions", t);
			// List<UserWalletResource> x =
			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "mystore/records";
	}

	@PostMapping("/searchRecords")
	public String searchRecords(@ModelAttribute("infoFilter") InfoFilter infoFilter, Model model) {
		try {
			System.out.print(infoFilter.getOrderBy());
			System.out.print(infoFilter.getCurrencyBy());
			System.out.print(infoFilter.getPayMethodBy());
			System.out.print(infoFilter.getRateTypeBy());
			System.out.print(infoFilter.getRegisterTypeBy());
			// List<Historial> historials = historialService.getAllHistorialBySellerId(1);
			historialService.getAllHistorialBySellerId(1);
			List<Transaction> t = new ArrayList<>();
			for (Historial x : historialService.getAllHistorialBySellerId(1)) {
				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
					t.add(transacT);
				}
			}
			Seller sel = new Seller();
			sel.setEmail("s");
			model.addAttribute("sellerInfo", infoFilter.getCurrencyBy());
			// model.addAttribute("sellerInfo",sellerService.getSellerById(1));
			List<UserWalletResource> listT = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
			if (listT != null) {
				Wallet x = new Wallet();

				model.addAttribute("transactions", transactionService.orderByObj(infoFilter, t));
			}
			// List<UserWalletResource> x =
			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/mystore/searchRecords";
	}
	@GetMapping("/new-client")
	public String viewNewClient(Model model) {
		try {
			fullInfoResource infoResource = new fullInfoResource();
			model.addAttribute("infoResource",infoResource);
		}catch(Exception e){
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/new-client";
	}
	@GetMapping("/new-payment")
	public String viewNewPayment(Model model) {
		try {
			fullInfoResource infoResource = new fullInfoResource();
			model.addAttribute("infoResource",infoResource);
		}catch(Exception e){
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/new-payment";
	}
	@GetMapping("/new-sell")
	public String viewNewSell(Model model) {
		try {
			fullInfoResource infoResource = new fullInfoResource();
			model.addAttribute("infoResource",infoResource);
		}catch(Exception e){
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/new-sell";
	}
	@GetMapping("/new-suscription")
	public String viewNewSubscription(Model model) {
		try {
			fullInfoResource infoResource = new fullInfoResource();
			model.addAttribute("infoResource",infoResource);
		}catch(Exception e){
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/new-suscription";
	}
	
	@PostMapping("registrarVentaByWallet")
	public String registrarVentaByWallet(@ModelAttribute("infoResource") fullInfoResource infoResource, SessionStatus status) {
		try {
			Date current = new Date();
			if (infoResource.getSale().getPaymentPay() == "credito") {
				//transactionService.createTransaction(transactionEntity, flowId);
				
				
			} else if (infoResource.getSale().getPaymentPay() == "contado") {
				if (infoResource.getDeliveryBool()) {
					if (infoResource.getSubscriptionBool()) {
						// NO TIENE SUBSCRIPCION POR ESO SE CREA UNA 
						subscriptionService.createSubscription(infoResource.getSubscription(), infoResource.getWallet().getId());
						//SI HAY SUSCRIPCION NO HAY MONTO DELIVERY
						infoResource.getDelivery().setDeliveryPrice(0F);
						Sale saleCreated = saleService.save(infoResource.getSale());
						deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
						//transactionService.AssignTransactionWithSale(transactionId, saleCreated.getId())
					} else {
						//VERIFICAR SI TIENE SUBS PARA NO COBRAR DELIVERY
						List<Subscription> subs = subscriptionService.getAllByWalletId(infoResource.getWallet().getId());
						if(subs != null) {
							Subscription subT = subs.get(subs.size()-1);
							//VERIFICAR VENCIMIENTO DE SUB
							if(subT.getExpirationDate().compareTo(current) > 0) {
								Sale saleCreated = saleService.save(infoResource.getSale());
								infoResource.getDelivery().setDeliveryPrice(0F);
								deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
							//SU SUB YA VENCIO
							} else {
								Sale saleCreated = saleService.save(infoResource.getSale());
								deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
							}
						} else {
							Sale saleCreated = saleService.save(infoResource.getSale());
							deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
						}
					}
				} else {
					//NI DELIVERY NI SUB .. SE AGREGA DIRECTO
					saleService.save(infoResource.getSale());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "";
	}
	@PostMapping("registrarVentaByNewClient")
	public String registrarVentaByNewClient(@ModelAttribute("infoResource") fullInfoResource infoResource, SessionStatus status) {
		try {
			
			if (infoResource.getSale().getPaymentPay() == "credito") {
				//transactionService.createTransaction(transactionEntity, flowId);
				
				
			} else if (infoResource.getSale().getPaymentPay() == "contado") {
				if (infoResource.getDeliveryBool()) {
					if (infoResource.getSubscriptionBool()) {
						//subscriptionService.createSubscription(subscription, walletId)
						deliveryService.createDelivery(infoResource.getDelivery(), infoResource.getWallet().getId());
					} else {
						
					}
				} else {

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "";
	}
	@PostMapping("registrarSubs")
	public String registrarSubs(@ModelAttribute("infoResource") fullInfoResource infoResource, SessionStatus status) {
		try {
			Subscription subs = new Subscription();
			Date current = new Date();
			subs.setCreationDate(current);
			subs.setAmount(infoResource.getSubscription().getAmount());
			subs.setSubscriptionType(infoResource.getSubscription().getSubscriptionType());
			subs.setExpirationDate(infoResource.getSubscription().getExpirationDate());
			subs.setWallet(infoResource.getWallet());
			subscriptionService.createSubscription(subs, infoResource.getWallet().getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "";
	}

	@Operation(summary = "Get seller by Id and UserId", description = "Get seller given Id and UserId", tags = {
			"sellers" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get seller given Id", content = @Content(mediaType = "application/json")) })
	@GetMapping("/users/{userId}/sellers/{sellerId}")
	public SellerResource getSellerByIdAndUserId(@PathVariable(name = "sellerId") Integer sellerId,
			@PathVariable(name = "userId") Integer userId) {
		return convertToResource(sellerService.getSellerByIdAndUserId(sellerId, userId));
	}

	@Operation(summary = "Get seller by business name", description = "Get seller given business name", tags = {
			"sellers" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get seller given business name", content = @Content(mediaType = "application/json")) })
	@GetMapping("/sellers/BusinessName={name}")
	public SellerResource getSellerByBusinessName(@PathVariable(name = "name") String name) {
		return convertToResource(sellerService.getSellerByBusinessName(name));
	}

	@Operation(summary = "Create seller", description = "Create seller for a User", tags = { "sellers" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Create seller for a User", content = @Content(mediaType = "application/json")) })
	@PostMapping("/users/{userId}/sellers")
	public SellerResource createSeller(@Valid @RequestBody SaveSellerResource resource,
			@PathVariable(name = "userId") Integer userId) {
		return convertToResource(sellerService.createSeller(convertToEntity(resource), userId));
	}

	@Operation(summary = "Update seller", description = "Update seller given UserId and Id", tags = { "sellers" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update seller given UserId and Id", content = @Content(mediaType = "application/json")) })
	@PutMapping("/users/{userId}/sellers/{sellerId}")
	public SellerResource updateSeller(@PathVariable(name = "sellerId") Integer sellerId,
			@PathVariable(name = "userId") Integer userId, @Valid @RequestBody SaveSellerResource resource) {
		return convertToResource(sellerService.updateSeller(sellerId, userId, convertToEntity(resource)));
	}

	@Operation(summary = "Delete seller", description = "Delete seller given Id", tags = { "sellers" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete seller given Id", content = @Content(mediaType = "application/json")) })
	@DeleteMapping("/sellers/{sellerId}")
	public ResponseEntity<?> deleteSeller(@PathVariable(name = "sellerId") Integer sellerId) {
		return sellerService.deleteSeller(sellerId);
	}

	/////////////////////////////////////////////////////////////////////////

	private Seller convertToEntity(SaveSellerResource resource) {
		return mapper.map(resource, Seller.class);
	}

	private SellerResource convertToResource(Seller entity) {
		return mapper.map(entity, SellerResource.class);
	}
}
