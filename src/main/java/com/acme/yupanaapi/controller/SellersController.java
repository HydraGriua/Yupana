package com.acme.yupanaapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.HistorialService;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.resource.InfoFilter;
import com.acme.yupanaapi.resource.SaveSellerResource;
import com.acme.yupanaapi.resource.SellerResource;
import com.acme.yupanaapi.resource.UserWalletResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Controller
@CrossOrigin
@RequestMapping("mystore")
public class SellersController {
	  //Mapeador de entidad con resource
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
    ////////////////////////////////////////////////////////////
    //Metodos crud para las llamadas

    @GetMapping("/records")
    public String getSellerById(Model model){
    	try {
    		InfoFilter infoFilter = new InfoFilter();
    		List<Transaction> t = new ArrayList<>();
    		historialService.getAllHistorialBySellerId(1);
    		for (Historial x :historialService.getAllHistorialBySellerId(1)) {
    			for(Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
    				 t.add(transacT);
    			}
    		}
    		model.addAttribute("infoFilter",infoFilter);
    		model.addAttribute("sellerInfo",sellerService.getSellerById(1));
    		model.addAttribute("transactions",t);
    		//List<UserWalletResource> x = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.err.print(e.getMessage());
    	}
    	return "mystore/records";
    }
    
    @PostMapping("/searchRecords")
    public String searchRecords(@ModelAttribute("infoFilter") InfoFilter infoFilter, Model model){
    	try {
    		System.out.print(infoFilter.getOrderBy());
    		System.out.print(infoFilter.getCurrencyBy());
    		System.out.print(infoFilter.getPayMethodBy());
    		System.out.print(infoFilter.getRateTypeBy());
    		System.out.print(infoFilter.getRegisterTypeBy());
    		//List<Historial> historials = historialService.getAllHistorialBySellerId(1);
    		historialService.getAllHistorialBySellerId(1);
    		List<Transaction> t = new ArrayList<>();
    		for (Historial x :historialService.getAllHistorialBySellerId(1)) {
    			for(Transaction transacT : transactionService.getAllByHistorialId(x.getId())) {
    				 t.add(transacT);
    			}
    		}
    		Seller sel = new Seller();
    		sel.setEmail("s");
    		model.addAttribute("sellerInfo",infoFilter.getCurrencyBy());
    		//model.addAttribute("sellerInfo",sellerService.getSellerById(1));
    		List<UserWalletResource> listT = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
    		if(listT != null) {
    			Wallet x = new Wallet();
    			
    			model.addAttribute("transactions",transactionService.orderByObj(infoFilter, t));
    		}
    		
    		//List<UserWalletResource> x = flowService.getAllUserTransactionById(walletService.getAllBySellerId(1));
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.err.print(e.getMessage());
    	}
    	return "/mystore/searchRecords";
    }
    @Operation(summary = "Get seller by Id and UserId", description ="Get seller given Id and UserId", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Get seller given Id", content =@Content(mediaType = "application/json") )
   	})
    @GetMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource getSellerByIdAndUserId(@PathVariable(name = "sellerId") Integer sellerId,  @PathVariable(name = "userId") Integer userId){
        return convertToResource(sellerService.getSellerByIdAndUserId(sellerId, userId));
    }
    
    @Operation(summary = "Get seller by business name", description ="Get seller given business name", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Get seller given business name", content =@Content(mediaType = "application/json") )
   	})
    @GetMapping("/sellers/BusinessName={name}")
    public SellerResource getSellerByBusinessName(@PathVariable(name = "name") String name){
        return convertToResource(sellerService.getSellerByBusinessName(name));
    }
    
    @Operation(summary = "Create seller", description ="Create seller for a User", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Create seller for a User", content =@Content(mediaType = "application/json") )
   	})
    @PostMapping("/users/{userId}/sellers")
    public SellerResource createSeller(@Valid @RequestBody SaveSellerResource resource, @PathVariable(name = "userId") Integer userId){
        return convertToResource(sellerService.createSeller(convertToEntity(resource),userId));
    }

    @Operation(summary = "Update seller", description ="Update seller given UserId and Id", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Update seller given UserId and Id", content =@Content(mediaType = "application/json") )
   	})
    @PutMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource updateSeller(@PathVariable(name = "sellerId") Integer sellerId, @PathVariable(name = "userId") Integer userId,@Valid @RequestBody SaveSellerResource resource){
        return convertToResource(sellerService.updateSeller(sellerId,userId,convertToEntity(resource)));
    }

    @Operation(summary = "Delete seller", description ="Delete seller given Id", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Delete seller given Id", content =@Content(mediaType = "application/json") )
   	})
    @DeleteMapping("/sellers/{sellerId}")
    public ResponseEntity<?> deleteSeller(@PathVariable(name="sellerId") Integer sellerId){
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
