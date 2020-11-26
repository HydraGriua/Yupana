package com.acme.yupanaapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.HistorialService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.helpers.UserTesting;
import com.acme.yupanaapi.resource.InfoFilter;
import com.acme.yupanaapi.resource.SaveSellerResource;
import com.acme.yupanaapi.resource.SellerResource;
import com.acme.yupanaapi.resource.UserWalletResource;
import com.acme.yupanaapi.resource.fullInfoResource;
import com.acme.yupanaapi.resource.NewClientResource;
import com.acme.yupanaapi.resource.NewPaymentResource;
import com.acme.yupanaapi.resource.NewSellResource;

import ch.qos.logback.core.net.server.Client;
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
	private UserService userService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private SubscriptionService subscriptionService;
	////////////////////////////////////////////////////////////
	// Metodos crud para las llamadas

//	@GetMapping("/records")
//	public String getSellerById(@RequestParam(name = "id", required = false) int id,Model model) {
//		try {
//			InfoFilter infoFilter = new InfoFilter();
//			List<Transaction> t = new ArrayList<>();
//			historialService.getAllHistorialBySellerId(id);
//			for (Historial x : historialService.getAllHistorialBySellerId(id)) {
//				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
//					t.add(transacT);
//				}
//			}
//			model.addAttribute("infoFilter", infoFilter);
//			model.addAttribute("sellerInfo", sellerService.getSellerById(id));
//			model.addAttribute("transactions", t);
//			model.addAttribute("idSession", UserTesting.Users.getIdSeller());
//			// List<UserWalletResource> x =
//			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getMessage());
//		}
//		return "mystore/records";
//	}
//	{id}/{orderyby}/{registerTypeBy}/{payMethodBy}/{currencyBy}
	@GetMapping("/records")
	public String setInfoResource(@RequestParam(name = "id", required = false) int id,
			@RequestParam(name = "orderby", required = false, defaultValue = "all") String orderby,
			@RequestParam(name = "registerTypeBy", required = false, defaultValue = "all") String registerTypeBy,
			@RequestParam(name = "payMethodBy", required = false, defaultValue = "all") String payMethodBy,
			@RequestParam(name = "rateTypeBy", required = false, defaultValue = "all") String rateTypeBy,
			@RequestParam(name = "currencyBy", required = false, defaultValue = "all") String currencyBy, Model model) {
		try {
			System.err.print(orderby + registerTypeBy + payMethodBy + currencyBy);
			System.err.print(orderby + registerTypeBy + payMethodBy + currencyBy);
			InfoFilter infoFilter = new InfoFilter();
			infoFilter.setCurrencyBy(currencyBy);
			infoFilter.setOrderBy(orderby);
			infoFilter.setPayMethodBy(payMethodBy);
			infoFilter.setRateTypeBy(rateTypeBy);
			infoFilter.setRegisterTypeBy(registerTypeBy);
			List<Transaction> t = new ArrayList<>();
			historialService.getAllHistorialBySellerId(id);
			for (Historial x : historialService.getAllHistorialBySellerId(id)) {
				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
					System.err.print("\n PRUEBA 2" + transacT);
					t.add(transacT);
				}
			}
//			System.err.print("\n PRUEBA 3: " + transactionService.orderByObj(infoFilter, t).get(0));
//			System.err.print("\n PRUEBA 3: " + transactionService.orderByObj(infoFilter, t).get(1));
//			System.err.print("\n PRUEBA 3: " + transactionService.orderByObj(infoFilter, t).get(2));
//			System.err.print("\n PRUEBA 3: " + transactionService.orderByObj(infoFilter, t).get(3));
//			System.err.print("\n PRUEBA 3: " + transactionService.orderByObj(infoFilter, t).get(4));
//			
			List<UserWalletResource> listT = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
			if (listT.size() > 0) {
				System.err.print("ERRORRRRRRRRRRR");
				System.err.print(t.get(0));
				Wallet x = new Wallet();
				model.addAttribute("transactions", transactionService.orderByObj(infoFilter, t));
				for(Transaction T : transactionService.orderByObj(infoFilter, t)) {
					System.err.print("\n PRUEBA 3: " + T);
				}
			}
			model.addAttribute("infoFilter", infoFilter);
			model.addAttribute("sellerInfo", sellerService.getSellerById(id));
			model.addAttribute("idSession", UserTesting.Users.getIdSeller());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		// http://localhost:8087/mystore/records?id=1&orderby=all&registerTypeBy=all&payMethodBy=payed&rateTypeBy=all&currencyBy=soles
		// http://localhost:8087/mystore/records?id=1&orderby=dateLasts&registerTypeBy=all&payMethodBy=payed&rateTypeBy=all&currencyBy=soles
		return "/mystore/records";
	}

//	@GetMapping("/records/{id}/{orderby}/{registerTypeBy}/{payMethodBy}/{currencyBy}")
//	public String setInfoOrder(@RequestParam(name = "id", required = false) int id,
//			@RequestParam(name = "orderby", required = false) String orderby,
//			@RequestParam(name = "registerTypeBy", required = false) String registerTypeBy,
//			@RequestParam(name = "payMethodBy", required = false) String payMethodBy,
//			@RequestParam(name = "currencyBy", required = false) String currencyBy,
//			Model model) {
//		try {
//			System.err.print(orderby + registerTypeBy + payMethodBy + currencyBy);
//			System.err.print(orderby + registerTypeBy + payMethodBy + currencyBy);
//			InfoFilter infoFilter = new InfoFilter();
//			List<Transaction> t = new ArrayList<>();
//			historialService.getAllHistorialBySellerId(id);
//			for (Historial x : historialService.getAllHistorialBySellerId(id)) {
//				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
//					t.add(transacT);
//				}
//			}
//			model.addAttribute("infoFilter", infoFilter);
//			model.addAttribute("sellerInfo", sellerService.getSellerById(id));
//			model.addAttribute("transactions", t);
//			model.addAttribute("idSession", UserTesting.Users.getIdSeller());
//			// List<UserWalletResource> x =
//			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
//			// List<UserWalletResource> x =
//			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getMessage());
//		}
//		return "mystore/records";
//	}
//	@PostMapping("/searchRecords")
//	public String searchRecords(@ModelAttribute("infoFilter") InfoFilter infoFilter, Model model) {
//		try {
//			System.err.print(infoFilter.getOrderBy());
//			System.err.print(infoFilter.getCurrencyBy());
//			System.err.print(infoFilter.getPayMethodBy());
//			System.err.print(infoFilter.getRateTypeBy());
//			System.err.print(infoFilter.getRegisterTypeBy());
//			// List<Historial> historials = historialService.getAllHistorialBySellerId(1);
//			historialService.getAllHistorialBySellerId(1);
//			List<Transaction> t = new ArrayList<>();
//			for (Historial x : historialService.getAllHistorialBySellerId(1)) {
//				for (Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
//					t.add(transacT);
//				}
//			}
////			Seller sel = new Seller();
////			sel.setEmail("s");
//			model.addAttribute("sellerInfo", infoFilter.getCurrencyBy());
//			// model.addAttribute("sellerInfo",sellerService.getSellerById(1));
//			List<UserWalletResource> listT = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
//			if (listT.size()>0) {
//				System.err.print("ERRORRRRRRRRRRR");
//				System.err.print(t.get(0));
//				Wallet x = new Wallet();
//				model.addAttribute("transactions", transactionService.orderByObj(infoFilter, t));
//				System.err.print(transactionService.orderByObj(infoFilter, t).get(0));
//			}
//			model.addAttribute("idSession", UserTesting.Users.getIdSeller());
//			// List<UserWalletResource> x =
//			// flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.print(e.getMessage());
//		}
//		return "redirect:/mystore/records";
//	}
	// CREACION DEL WALLET
	@GetMapping("/newClient")
	public String viewNewClient(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			NewClientResource infoClientResource = new NewClientResource();
			List<Wallet> listW = walletService.getAllBySellerId(id);
			System.err.print("CLIENTE :;;;;;;;;;;;;");
			model.addAttribute("clientModel", infoClientResource);
			model.addAttribute("listaUsuarios", listW);
			model.addAttribute("idSession", id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "mystore/newClient";
	}

	public String converter(float value) {
		if (value == 1) {
			return "Diario";
		} else if (value == 7) {
			return "Semanal";
		} else if (value == 15) {
			return "Quincenal";
		} else if (value == 30) {
			return "Mensual";
		} else if (value == 60) {
			return "Bimestral";
		} else if (value == 90) {
			return "Trimestral";
		} else if (value == 120) {
			return "Cuatrimestral";
		} else if (value == 180) {
			return "Semestral";
		} else if (value == 360) {
			return "Anual";
		} else {
			return "UNDEFINED";
		}
	}
	public String converterTasa(String string) {
		if(string.equals("simple")) {
			return "Tasa de Interés Simple";
		}
		else if(string.equals("nominal")) {
			return "Tasa de Interés Compuesta";
		}
		else if(string.equals("efectiva")) {
			return "Tasa de Interés Efectiva";
		}else {
			return string;
		}
	}
	@PostMapping("/registerNewClient")
	public String registerNewClient(@ModelAttribute("clientModel") NewClientResource client, Model model) {
		try {
			// client.getWallet().getUser().get
			// client.getFlow().getRatePeriod();
			
			client.getWallet().setMaintenancePeriodType(converter(client.getWallet().getMaintenancePeriod()));
			client.getFlow().setCapitalizationType(converter(client.getFlow().getCapitalization()));
			client.getFlow().setRatePeriodType(converter(client.getFlow().getRatePeriod()));
			// client.getFlow().setRateType(converter(client.getFlow().getRatePeriod()));
			client.getWallet().setState("ACTIVE");
			client.getFlow().setCurrentCreditLine(client.getFlow().getCreditLine());
			client.getFlow().setTotalDebt(0F);
			//
			client.getFlow().setRateType(converterTasa(client.getFlow().getRateType()));
			client.getWallet().setSeller(sellerService.getSellerById(UserTesting.Users.getIdSeller()));
			client.getWallet().setType("BASICO");
			client.getWallet().setBalance(0F);
			//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			//System.err.println("\n fecha" + date);
			client.getWallet().setCreationDate(date);
			System.err.print("CLIENTE :;;;;;;;;;;;;" + client.getWallet().getUser());
			System.err.print("\nCLIENTE :;;;;;;;;;;;;" + client.getWallet());
			System.err.print("\nCLIENTE :;;;;;;;;;;;;" + client.getFlow());
			client.getWallet().getUser().setId(0);
			client.getFlow().setDeadlineDate(date);
			client.getFlow().setLastTransactionDate(date);
			client.getFlow().setState("ACTIVO");
//			Historial historialT = new Historial();
//			historialT.setId(0);
			// historialService.save(historialT, UserTesting.Users.getIdSeller());
			client.getWallet().getUser().setId(0);
			Wallet walletT = walletService.createWallet(client.getWallet(), UserTesting.Users.getIdSeller(),
					userService.createUser(client.getWallet().getUser()).getId());
			flowService.createFlow(client.getFlow(), walletT.getId(), UserTesting.Users.getIdSeller());
			//client.setSubscriptionBool(true);
			System.err.print("\n\n   SUSCRIPTION:"+client.getSubscription().getSubscriptionPeriod());
			if (!client.getSubscription().getSubscriptionPeriod().equals(0.0f)) {
				Subscription subs = new Subscription();
				subs.setCreationDate(date);
				subs.setExpirationDate(date);
				subs.setSubscriptionType("TIPO");
				subs.setSubscriptionPeriod(client.getSubscription().getSubscriptionPeriod());
				subs.setSubscriptionPeriodType(converter(client.getSubscription().getSubscriptionPeriod()));
				subs.setAmount(client.getSubscription().getAmount());
				System.err.print("\nCLIENTE :;;;;;;;;;;;;" + subs);
				subscriptionService.createSubscription(subs, walletT.getId());
			}
			System.err.print("\nFIN :;;;;;;;;;;;;");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/clients?id=" + UserTesting.Users.getIdSeller();
	}

	// FIN DE CREACION DE WALLET
	// CREACION DE LA VENTA
	@GetMapping("/newSell")
	public String viewNewSell(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			NewSellResource infoSellResource = new NewSellResource();
			List<Wallet> listW = walletService.getAllBySellerId(1);
			Transaction tra = new Transaction();
			Wallet wallet = new Wallet();
			// infoSellResource.getDelivery().setDeliveryPrice(0F);
			model.addAttribute("sellModel", infoSellResource);
			model.addAttribute("listaUsuarios", listW);
			model.addAttribute("walletL", wallet);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "mystore/newSell";
	}
	public String converterTranName(String string) {
		if (string.equals("sell")) {
			return "Registro de venta al crédito";
		} else if (string.equals("delivery")) {
			return "Registro de venta al crédito con Delivery";
		} else if (string.equals("mantenimiento")) {
			return "Mantenimiento de cuenta";
		} else if (string.equals("suscripciond")) {
			return "Suscripción a Delivery";
		} else if (string.equals("amortizaciones")) {
			return "Amortizaciones de deuda";
		} else if (string.equals("contado")) {
			return "Venta al contado";
		} else {
			return string;
		}
	}
	
	@PostMapping("/registerNewSell")
	public String registerNewSell(@ModelAttribute("sellModel") NewSellResource sellModel, Model model) {
		try {
			System.out.print(sellModel);
			NewSellResource infoSellResource = new NewSellResource();
			List<Wallet> listW = walletService.getAllBySellerId(1);
			Transaction tra = new Transaction();
			Wallet wallet = new Wallet();
			System.err.print("\n" + sellModel.getWallet());
			
			System.err.print("\n" + sellModel.getDelivery());
//			model.addAttribute("sellModel", infoSellResource);
//			model.addAttribute("listaUsuarios", listW);
//			model.addAttribute("walletL", wallet);
			Flow flowT = flowService.getActiveFlow(sellModel.getWallet().getId());
			Transaction transactionT = sellModel.getTransaction();
			System.err.print("\n direccion delv" +sellModel.getDelivery().getDirection());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			//System.err.println(dateFormat.format(date));
			//asignar info del flow al transac
			transactionT.setTransactionDate(date);
			transactionT.setInterestRate(flowT.getInterestRate());
			transactionT.setCapitalization(flowT.getCapitalization());
			transactionT.setCapitalizationType(flowT.getCapitalizationType());
			transactionT.setRateType(flowT.getRateType());
			if(sellModel.getTransaction().getTransactionName().equals("contado")) {
				transactionT.setPayType("contado");
			}else {
				transactionT.setPayType("credito");	
			}
			transactionT.setTransactionName(converterTranName(sellModel.getTransaction().getTransactionName()));
			transactionT.setDescription("descripcion DEFAULT");
			transactionT.setFlow(flowT);
			transactionT.setDebt(sellModel.getTransaction().getDebt());
			transactionT.setNetAmount(sellModel.getTransaction().getAmountPaid());
			transactionT.setCurrencyType(flowT.getWallet().getCurrencyType());
			System.err.print("\n TRANSACTON 2020" + transactionT);
			//System.err.print("\n "+ sellModel.getTransaction().ge);
			System.err.print("\n" + sellModel.getTransaction());
			//
			Historial historialT = new Historial();
			historialT.setIdOfSeller(UserTesting.Users.getIdSeller());
			historialT.setSeller(sellerService.getSellerById(UserTesting.Users.getIdSeller()));
			sellModel.getTransaction().setHistorial(historialService.save(historialT, UserTesting.Users.getIdSeller()));
			
			Transaction tX = transactionService.createTransaction(sellModel.getTransaction(), flowT.getId());
			System.err.print("\n" + tX);
			//
			System.err.print("\n direccion delv" +sellModel.getDelivery().getDirection());
			if (!sellModel.getDelivery().getDirection().equals("")
				&& sellModel.getDelivery().getDescription().equals("")) {
				// FALTA OBETNER LA FECHA Y HORA
				System.err.print("EUUUUUUUUUUUUU123123123");
				//deliveryService.createDelivery(sellModel.getDelivery(), tX.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/clients?id=" + UserTesting.Users.getIdSeller();
	}

	////////// REGISTRAR SUBS
	@GetMapping("/newSubscription")
	public String viewNewSubscription(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			Subscription sub = new Subscription();
			List<Wallet> listW = walletService.getAllBySellerId(UserTesting.Users.getIdSeller());
			fullInfoResource infoResource = new fullInfoResource();

			if (listW.size() > 0) {
				model.addAttribute("listWallet", listW);
			}
			model.addAttribute("sub", sub);
			model.addAttribute("idSession", UserTesting.Users.getIdSeller());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "mystore/newSubscription";
	}

	@PostMapping("/newRegisterSub")
	public String saveNewSubscription(@ModelAttribute("sub") Subscription sub, Model model) {
		try {
			fullInfoResource infoResource = new fullInfoResource();
			System.err.print("\n" + sub);
			System.err.print("\n" + sub.getWallet().getId());
			model.addAttribute("infoResource", infoResource);
			System.err.print("\n" + sub);
			sub.setSubscriptionPeriodType(converter(sub.getSubscriptionPeriod()));
			// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			sub.setCreationDate(date);
			sub.setExpirationDate(date);
			System.err.print("\n" + sub.getExpirationDate());
			subscriptionService.createSubscription(sub, sub.getWallet().getId());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/clients?id=" + UserTesting.Users.getIdSeller();
	}

	///////// TERMINAR REG SUB
	///////// REG payment
	@GetMapping("/newPayment")
	public String viewNewPayment(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			Flow flow = new Flow();
			NewPaymentResource newT = new NewPaymentResource();
//			Transaction tran = new Transaction();
//			tran.get
			// fullInfoResource infoResource = new fullInfoResource();
			// newT.getTransaction().getPayType();
			// newT.setWalletId();
			flow = flowService.getActiveFlow(id);
			// flowService.getActiveFlow(id);
			// newT.getFlow().setId(flow.getId());
			// model.addAttribute("wallet", walletService.getWalletById(id));
			// newT.setFlow(flowService.getActiveFlow(id));
			newT.setIdSeller(UserTesting.Users.getIdSeller());
			newT.setFlowId(flow.getId());
			newT.setIdWallet(flow.getId());
			
			model.addAttribute("infoPayment", newT);
			model.addAttribute("flowModel", flow);
			model.addAttribute("idWallet", id);
			model.addAttribute("flowId", flow.getId());
			// model.addAttribute("idSession", UserTesting.Users.getIdSeller());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "mystore/newPayment";
	}

	@PostMapping("/registerNewPayment")
	public String registerNewPayment(@ModelAttribute("payModel") NewPaymentResource sub, Model model) {
		try {
//			fullInfoResource infoResource = new fullInfoResource();
//			model.addAttribute("infoResource", infoResource);
			System.err.print("HOLAeeeeeeeee");
			System.err.print("\n" + sub.getTransaction());
			System.err.print("\n FLUJO UFFFFFFFFF" + sub.getFlowId());
//			System.err.print("\n"+sub.getFlow());
//			System.err.print("\nFLUJO IDDDDDDDDD"+sub.getFlow().getId());
			System.err.print("\n ID WLALLET " + sub.getIdWallet());
			System.err.print("\n AMOUNT" + sub.getAmountToPay());
			System.err.print("\n DESCRP" + sub.getDescription());
			System.err.print("\n IDD SELLER--- " + sub.getIdSeller() + "\n");
			Transaction x = new Transaction();
			String strAmount = sub.getAmountToPay();
			float amount = Float.parseFloat(strAmount);
			// or Float.valueOf(string)
			x.setAmountPaid(amount);
			x.setDescription(sub.getDescription());
			x.setTransactionName(converterTranName(sub.getTransactionType()));
			x.setPayType("contado");
			Flow xT = flowService.getFlowById(sub.getFlowId());
			x.setCapitalization(xT.getCapitalization());
			x.setRatePeriod(xT.getRatePeriod());
			x.setInterestRate(xT.getInterestRate());
			Date date = new Date();
			x.setTransactionDate(date);
			x.setRateType(xT.getRateType());
			x.setCapitalizationType(xT.getCapitalizationType());
			transactionService.createTransaction(x, sub.getFlowId());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/clients?id=" + sub.getIdSeller();
	}

	@PostMapping("registrarVentaByWallet")
	public String registrarVentaByWallet(@ModelAttribute("infoResource") fullInfoResource infoResource,
			SessionStatus status) {
		try {
//			Date current = new Date();
//			if (infoResource .getPaymentPay() == "credito") {
//				//transactionService.createTransaction(transactionEntity, flowId);
//				
//			} else if (infoResource.getSale().getPaymentPay() == "contado") {
//				if (infoResource.getDeliveryBool()) {
//					if (infoResource.getSubscriptionBool()) {
//						// NO TIENE SUBSCRIPCION POR ESO SE CREA UNA 
//						subscriptionService.createSubscription(infoResource.getSubscription(), infoResource.getWallet().getId());
//						//SI HAY SUSCRIPCION NO HAY MONTO DELIVERY
//						infoResource.getDelivery().setDeliveryPrice(0F);
//						Sale saleCreated = saleService.save(infoResource.getSale());
//						deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
//						//transactionService.AssignTransactionWithSale(transactionId, saleCreated.getId())
//					} else {
//						//VERIFICAR SI TIENE SUBS PARA NO COBRAR DELIVERY
//						List<Subscription> subs = subscriptionService.getAllByWalletId(infoResource.getWallet().getId());
//						if(subs != null) {
//							Subscription subT = subs.get(subs.size()-1);
//							//VERIFICAR VENCIMIENTO DE SUB
//							if(subT.getExpirationDate().compareTo(current) > 0) {
//								Sale saleCreated = saleService.save(infoResource.getSale());
//								infoResource.getDelivery().setDeliveryPrice(0F);
//								deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
//							//SU SUB YA VENCIO
//							} else {
//								Sale saleCreated = saleService.save(infoResource.getSale());
//								deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
//							}
//						} else {
//							Sale saleCreated = saleService.save(infoResource.getSale());
//							deliveryService.createDelivery(infoResource.getDelivery(), saleCreated.getId());
//						}
//					}
//				} else {
//					//NI DELIVERY NI SUB .. SE AGREGA DIRECTO
//					saleService.save(infoResource.getSale());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "";
	}

	@PostMapping("registrarVentaByNewClient")
	public String registrarVentaByNewClient(@ModelAttribute("infoResource") fullInfoResource infoResource,
			SessionStatus status) {
		try {

//			if (infoResource.getSale().getPaymentPay() == "credito") {
//				//transactionService.createTransaction(transactionEntity, flowId);
//				
//			} else if (infoResource.getSale().getPaymentPay() == "contado") {
//				if (infoResource.getDeliveryBool()) {
//					if (infoResource.getSubscriptionBool()) {
//						//subscriptionService.createSubscription(subscription, walletId)
//						deliveryService.createDelivery(infoResource.getDelivery(), infoResource.getWallet().getId());
//					} else {
//						
//						
//					}
//				} else {
//
//				}
//			}

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
//			subs.setCreationDate(current);
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
