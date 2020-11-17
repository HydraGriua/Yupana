package com.acme.yupanaapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.resource.SaveSubscriptionResource;
import com.acme.yupanaapi.resource.SubscriptionResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SubscriptionsController {

    @Autowired
    private ModelMapper mapper;

    //Services
    @Autowired
    private SubscriptionService subscriptionService;
    
    
    @Operation(summary = "Get all Subscription",description = "Get All Subscription",tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All Subscription",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/wallets/{walletId}/subscriptions")
    public List<SubscriptionResource> getAllSubscriptionByWalletId(@PathVariable(name ="walletId") Long walletId){
    	return subscriptionService.getAllByWalletId(walletId).stream().map(this::convertToResource).collect(Collectors.toList());
    }
	
    @Operation(summary = "Create Subscription",description = "Create Subscription for a Wallet",tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Subscription for a Wallet",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/wallets/{walletId}/subscriptions")
    public SubscriptionResource createSubscription( @PathVariable(name = "walletId") Long walletId, @Valid @RequestBody SaveSubscriptionResource resource){
        return convertToResource(subscriptionService.createSubscription( convertToEntity(resource),walletId));
    }
    
    @Operation(summary = "Update Subscription",description = "Update Subscription By Id and Wallet Id",tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Subscription By Id and Wallet Id",content =@Content(mediaType = "application/json"))
    })
    @PutMapping("/wallets/{walletId}/subscriptions/{subscriptionId}")
    public SubscriptionResource updateSubscription( @PathVariable(name ="walletId") Long walletId, @PathVariable(name="subscriptionId") Long subscriptionId, @Valid @RequestBody SaveSubscriptionResource resource){
    	return convertToResource( subscriptionService.updateSubscription(convertToEntity(resource), subscriptionId, walletId));
    }
    
    @Operation(summary = "Delete Subscription",description = "Delete Subscription By Id",tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Subscription By Id",content =@Content(mediaType = "application/json"))
    })
    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable(name ="subscriptionId") Long subscriptionId){
    	return subscriptionService.deleteSubscription(subscriptionId);
    }
    
	//convert 
    private Subscription convertToEntity(SaveSubscriptionResource resource) {
        return mapper.map(resource, Subscription.class);
    }

    private SubscriptionResource convertToResource(Subscription entity) {
        return mapper.map(entity, SubscriptionResource.class);
    }
	
}
