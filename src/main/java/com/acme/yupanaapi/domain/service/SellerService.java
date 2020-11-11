package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Seller;
import org.springframework.http.ResponseEntity;

public interface SellerService {
    Seller getSellerByIdAndUserId(Long sellerId, Long userId);
    Seller getSellerById(Long sellerId);
    Seller getSellerByBusinessName(String businessName);
    Seller createSeller(Seller seller, Long userId);
    Seller updateSeller(Long sellerId, Long userId, Seller sellerRequest);
    ResponseEntity<?> deleteSeller(Long sellerId);
}

