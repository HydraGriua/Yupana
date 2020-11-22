package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.SellerRepository;
import com.acme.yupanaapi.domain.repository.UserRepository;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserRepository userRepository;
    
    @Transactional(readOnly = true)
    @Override
    public Seller getSellerByIdAndUserId(Integer sellerId, Integer userId) {
        return sellerRepository.findByIdAndUserId(sellerId,userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller not found with Id " + sellerId +
                                " and UserId " + userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Seller getSellerById(Integer sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller", "Id", sellerId));
    }

    @Transactional(readOnly = true)
    @Override
    public Seller getSellerByBusinessName(String businessName) {
        return sellerRepository.findByBusinessName(businessName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller", "Business Name", businessName));
    }

    @Transactional
    @Override
    public Seller createSeller(Seller seller, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "User", "Id", userId));
        seller.setUser(user);
        return sellerRepository.save(seller);
    }

    @Transactional
    @Override
    public Seller updateSeller(Integer sellerId, Integer userId, Seller sellerRequest) {
        if(!userRepository.existsById(userId))
            throw  new ResourceNotFoundException("User", "Id", userId);
        return sellerRepository.findById(sellerId).map(seller -> {
            seller.setEmail(sellerRequest.getEmail());
            seller.setOldPassword(seller.getActualPassword());
            seller.setActualPassword(sellerRequest.getActualPassword());
            seller.setStoreAdress(sellerRequest.getStoreAdress());
            seller.setBusinessName(sellerRequest.getBusinessName());
            return sellerRepository.save(seller);
        }).orElseThrow(() -> new ResourceNotFoundException(
                        "Seller", "Id", sellerId));
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteSeller(Integer sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller not found with Id " + sellerId));
        sellerRepository.delete(seller);
        return ResponseEntity.ok().build();
    }
    
}
