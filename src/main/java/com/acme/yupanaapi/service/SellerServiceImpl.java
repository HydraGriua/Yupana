package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.repository.SellerRepository;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;


    @Override
    public Seller getSellerByIdAndUserId(Long sellerId, Long userId) {
        return sellerRepository.findByIdAndUserId(sellerId,userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with Id " + sellerId +
                                " and UserId " + userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Seller getSellerById(Long sellerId) {
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

    @Override
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSeller(Long sellerId, Seller sellerRequest) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller", "Id", sellerId));
        seller.setEmail(sellerRequest.getEmail());
        seller.setOldPassword(seller.getActualPassword());
        seller.setActualPassword(sellerRequest.getActualPassword());
        seller.setStoreAdress(sellerRequest.getStoreAdress());
        seller.setBusinessName(sellerRequest.getBusinessName());
        return sellerRepository.save(seller);

        //TODO: verificar posible metodo con mapping
    }

    @Override
    public ResponseEntity<?> deleteSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller not found with Id " + sellerId));
        sellerRepository.delete(seller);
        return ResponseEntity.ok().build();
    }
}
