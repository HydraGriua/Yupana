package com.acme.yupanaapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.helpers.UserTesting;
import com.acme.yupanaapi.resource.InfoFilter;
import com.acme.yupanaapi.resource.UserWalletResource;
import com.acme.yupanaapi.resource.NewClientResource;
import com.acme.yupanaapi.resource.NewPaymentResource;
import com.acme.yupanaapi.resource.NewSellResource;

@Controller
@CrossOrigin
@RequestMapping("mystore")
public class SellersController {
	// Mapeador de entidad con resource

	@Autowired
	private SellerService sellerService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UserService userService;
	////////////////////////////////////////////////////////////
	// Metodos crud para las llamadas

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
				for (Transaction transacT : transactionService.getAllBySellerId(id)) {
					System.err.print("\n PRUEBA 2" + transacT);
					t.add(transacT);
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
			client.getFlow().setRatePeriod(client.getFlow().getRatePeriod());
			client.getWallet().setState("ACTIVE");
			client.getFlow().setCurrentCreditLine(client.getFlow().getCreditLine());
			client.getFlow().setTotalDebt(0F);
			System.err.print(client.getFlow());
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
			client.getWallet().getUser().setId(0);
			Wallet walletT = walletService.createWallet(client.getWallet(), UserTesting.Users.getIdSeller(),
					userService.createUser(client.getWallet().getUser()).getId());
			flowService.createFlow(client.getFlow(), walletT.getId(), UserTesting.Users.getIdSeller());
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
		if (string.equals("credito")) {
			return "Registro de venta al crédito";
		} else if (string.equals("mantenimiento")) {
			return "Mantenimiento de cuenta";
		} else if (string.equals("amortizaciones")) {
			return "Amortizaciones de deuda";
		} else if (string.equals("contado")) {
			return "Venta al contado";
		} else {
			return string;
		}
	}
    @GetMapping("/newTicket")
    public String viewNewPaymentTicket(@RequestParam(name = "id", required = false) int id, Model model) {
        try {
            //GET LAST CLIENT
            Transaction transaction = transactionService.getTransactionById(id);
            model.addAttribute("transaction",transaction);
            model.addAttribute("idSession",transaction.getFlow().getWallet().getSeller().getId());
        }catch(Exception e){
            e.printStackTrace();
            System.err.print(e.getMessage());
        }
        return "/mystore/newTicket";
    }
    
	@PostMapping("/registerNewSell")
	public String registerNewSell(@ModelAttribute("sellModel") NewSellResource sellModel, Model model) {
		Transaction tX = new Transaction();
		try {
			System.out.print(sellModel);
			System.err.print("\n" + sellModel.getWallet());
		    System.err.print("\n\nFECHA DE CRECION: " + sellModel.getCreationDate() + "\n");
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date convertedCurrentDate = sdf.parse(sellModel.getCreationDate());
			Flow flowT = flowService.getActiveFlow(sellModel.getWallet().getId());
			Transaction transactionT = sellModel.getTransaction();
			//Date date = new Date();
			//transactionT.setTransactionDate(date);
			transactionT.setInterestRate(flowT.getInterestRate());
			transactionT.setCapitalization(flowT.getCapitalization());
			transactionT.setCapitalizationType(flowT.getCapitalizationType());
			transactionT.setTransactionDate(convertedCurrentDate);
			transactionT.setRateType(flowT.getRateType());
			transactionT.setRatePeriod(flowT.getRatePeriod());
			transactionT.setPayType(sellModel.getTransaction().getTransactionName());
			transactionT.setTransactionName(converterTranName(sellModel.getTransaction().getTransactionName()));
			transactionT.setDescription("DESCRIPCION TRANSACION");
			transactionT.setFlow(flowT);
			transactionT.setDebt(sellModel.getTransaction().getDebt());
			transactionT.setAmountPaid(sellModel.getTransaction().getAmountPaid());
			transactionT.setNetAmount(transactionT.getDebt()+transactionT.getAmountPaid());
			transactionT.setCurrencyType(flowT.getWallet().getCurrencyType());
			
			System.err.print("\n TRANSACTON 2020" + transactionT);
			System.err.print("\n" + sellModel.getTransaction());
			
			transactionT.setSeller(sellModel.getWallet().getSeller());
			tX = transactionService.createTransaction(sellModel.getTransaction(), flowT.getId());
			System.err.print("\n" + tX);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/newTicket?id=" + tX.getId();
	}
	
	///////// REG payment
	@GetMapping("/newPayment")
	public String viewNewPayment(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			Flow flow = new Flow();
			NewPaymentResource newT = new NewPaymentResource();
			flow = flowService.getActiveFlow(id);
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
		Transaction newT = new Transaction();
		try {
//			fullInfoResource infoResource = new fullInfoResource();
//			model.addAttribute("infoResource", infoResource);
//			System.err.print("HOLAeeeeeeeee");
//			System.err.print("\n" + sub.getTransaction());
//			System.err.print("\n FLUJO UFFFFFFFFF" + sub.getFlowId());
//			System.err.print("\n ID WLALLET " + sub.getIdWallet());
//			System.err.print("\n AMOUNT" + sub.getAmountToPay());
//			System.err.print("\n DESCRP" + sub.getDescription());
//			System.err.print("\n IDD SELLER--- " + sub.getIdSeller() + "\n");
			Transaction x = new Transaction();
			String strAmount = sub.getAmountToPay();
			float amount = Float.parseFloat(strAmount);
			x.setAmountPaid(amount);
			x.setDebt(amount * (-1));
			x.setNetAmount(amount * (-1));
			x.setDescription(sub.getDescription());
			x.setTransactionName(converterTranName(sub.getTransactionType()));
			x.setPayType("contado");
			Flow xT = flowService.getFlowById(sub.getFlowId());
			x.setCurrencyType("soles");
			x.setCapitalization(xT.getCapitalization());
			x.setRatePeriod(xT.getRatePeriod());
			x.setInterestRate(xT.getInterestRate());
			Date date = new Date();
			x.setTransactionDate(date);
			x.setRateType(xT.getRateType());
			x.setCapitalizationType(xT.getCapitalizationType());
			newT = transactionService.createTransaction(x, sub.getFlowId());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "redirect:/mystore/newTicket?id=" + newT.getId();
		
	}
}
