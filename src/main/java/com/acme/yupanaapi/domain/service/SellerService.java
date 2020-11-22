package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Seller;
import org.springframework.http.ResponseEntity;

public interface SellerService {
    Seller getSellerByIdAndUserId(Integer sellerId, Integer userId);
    Seller getSellerById(Integer sellerId);
    Seller getSellerByBusinessName(String businessName);
    Seller createSeller(Seller seller, Integer userId);
    Seller updateSeller(Integer sellerId, Integer userId, Seller sellerRequest);
    ResponseEntity<?> deleteSeller(Integer sellerId);
}

