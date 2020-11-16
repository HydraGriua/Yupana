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

    @GetMapping("/sellers/{sellerId}")
    public SellerResource getSellerById(@PathVariable(name = "sellerId") Long sellerId){
        return convertToResource(sellerService.getSellerById(sellerId));
    }

    @GetMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource getSellerByIdAndUserId(@PathVariable(name = "sellerId") Long sellerId,  @PathVariable(name = "userId") Long userId){
        return convertToResource(sellerService.getSellerByIdAndUserId(sellerId, userId));
    }
    
    @GetMapping("/sellers/BusinessName={name}")
    public SellerResource getSellerByBusinessName(@PathVariable(name = "name") String name){
        return convertToResource(sellerService.getSellerByBusinessName(name));
    }
    @PostMapping("/users/{userId}/sellers")
    public SellerResource createSeller(@Valid @RequestBody SaveSellerResource resource, @PathVariable(name = "userId") Long userId){
        return convertToResource(sellerService.createSeller(convertToEntity(resource),userId));
    }

    @PutMapping("/users/{userId}/sellers/{sellerId}")
    public SellerResource updateSeller(@PathVariable(name = "sellerId") Long sellerId, @PathVariable(name = "userId") Long userId,@Valid @RequestBody SaveSellerResource resource){
        return convertToResource(sellerService.updateSeller(sellerId,userId,convertToEntity(resource)));
    }

    @DeleteMapping("/sellers/{sellerId}")
    public ResponseEntity<?> deleteSeller(@PathVariable(name="id") Long sellerId){
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
