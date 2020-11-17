package com.acme.yupanaapi.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.resource.SaveSellerResource;
import com.acme.yupanaapi.resource.SellerResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api")
public class SellersController {
	  //Mapeador de entidad con resource
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private SellerService sellerService;


    ////////////////////////////////////////////////////////////
    //Metodos crud para las llamadas
    @Operation(summary = "Get seller by Id", description ="Get seller given Id", tags = {"sellers"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get seller given Id", content =@Content(mediaType = "application/json") )
	})
    @GetMapping("/sellers/{sellerId}")
    public SellerResource getSellerById(@PathVariable(name = "sellerId") Long sellerId){
        return convertToResource(sellerService.getSellerById(sellerId));
    }

    @Operation(summary = "Get seller by Id and UserId", description ="Get seller given Id and UserId", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Get seller given Id", content =@Content(mediaType = "application/json") )
   	})
    @GetMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource getSellerByIdAndUserId(@PathVariable(name = "sellerId") Long sellerId,  @PathVariable(name = "userId") Long userId){
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
    public SellerResource createSeller(@Valid @RequestBody SaveSellerResource resource, @PathVariable(name = "userId") Long userId){
        return convertToResource(sellerService.createSeller(convertToEntity(resource),userId));
    }

    @Operation(summary = "Update seller", description ="Update seller given UserId and Id", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Update seller given UserId and Id", content =@Content(mediaType = "application/json") )
   	})
    @PutMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource updateSeller(@PathVariable(name = "sellerId") Long sellerId, @PathVariable(name = "userId") Long userId,@Valid @RequestBody SaveSellerResource resource){
        return convertToResource(sellerService.updateSeller(sellerId,userId,convertToEntity(resource)));
    }

    @Operation(summary = "Delete seller", description ="Delete seller given Id", tags = {"sellers"})
   	@ApiResponses(value = {
   			@ApiResponse(responseCode = "200", description = "Delete seller given Id", content =@Content(mediaType = "application/json") )
   	})
    @DeleteMapping("/sellers/{sellerId}")
    public ResponseEntity<?> deleteSeller(@PathVariable(name="sellerId") Long sellerId){
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
