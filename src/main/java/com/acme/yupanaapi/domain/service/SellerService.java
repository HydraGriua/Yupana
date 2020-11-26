package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Seller;
import org.springframework.http.ResponseEntity;

public interface SellerService {
    Seller getSellerByIdAndUserId(int sellerId, int userId);
    Seller getSellerById(int sellerId);
    Seller getSellerByBusinessName(String businessName);
    Seller createSeller(Seller seller, int userId);
    Seller updateSeller(int sellerId, int userId, Seller sellerRequest);
    ResponseEntity<?> deleteSeller(int sellerId);
    Seller getSellerByUsername(String username);
    public void UpdateResetPasswordToken(String token, String email);
    public Seller getByResetPasswordToken(String token);
    public void UpdatePassword(Seller seller, String newPassword);
}

