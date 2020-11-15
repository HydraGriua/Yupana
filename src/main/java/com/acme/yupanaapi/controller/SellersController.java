package com.acme.yupanaapi.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.resource.SaveSellerResource;
import com.acme.yupanaapi.resource.SellerResource;


@RestController
public class SellersController {
	  //Mapeador de entidad con resource
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private SellerService sellerService;


    ////////////////////////////////////////////////////////////
    //Metodos crud para las llamadas

    @GetMapping("/sellers/{id}")
    public SellerResource getSellerById(@PathVariable(name = "id") Long sellerId){
        return convertToResource(sellerService.getSellerById(sellerId));
    }

    @GetMapping("/sellers/dni={dni}")
    public SellerResource getSellerByIdAndUserId(@PathVariable(name = "dni") Long sellerId,  @PathVariable(name = "userId") Long userId){
        return convertToResource(sellerService.getSellerByIdAndUserId(sellerId, userId));
    }
    
    @GetMapping("/sellers/{id}")
    public SellerResource getSellerByBusinessName(@PathVariable(name = "id") String string){
        return convertToResource(sellerService.getSellerByBusinessName(string));
    }
    @PostMapping("/sellers")
    public SellerResource createSeller(@Valid @RequestBody SaveSellerResource resource, @PathVariable(name = "userId") Long userId){
        return convertToResource(sellerService.createSeller(convertToEntity(resource),userId));
    }

    @PutMapping("/sellers/{id}")
    public SellerResource updateSeller(@PathVariable(name = "id") Long sellerId, @PathVariable(name = "userId") Long userId,@Valid @RequestBody SaveSellerResource resource){
        return convertToResource(sellerService.updateSeller(sellerId,userId,convertToEntity(resource)));
    }

    @DeleteMapping("/sellers/{id}")
    public ResponseEntity<?> deleteSeller(@PathVariable(name="id") Long sellerId){
        return sellerService.deleteSeller(sellerId);
    }
    
    @DeleteMapping("/sellers/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="userId") Long userId){
        return sellerService.deleteUser(userId);
    }
    
    
    /////////////////////////////////////////////////////////////////////////


    private Seller convertToEntity(SaveSellerResource resource) {
        return mapper.map(resource, Seller.class);
    }

    private SellerResource convertToResource(Seller entity) {
        return mapper.map(entity, SellerResource.class);
    }
}
