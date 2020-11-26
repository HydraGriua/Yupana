package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.SellerRepository;
import com.acme.yupanaapi.domain.repository.UserRepository;
import com.acme.yupanaapi.domain.service.SellerService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	private  BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Transactional(readOnly = true)
    @Override

    public Seller getSellerByIdAndUserId(int sellerId, int userId) {
        return sellerRepository.findByIdAndUserId(sellerId,userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller not found with Id " + sellerId +
                                " and UserId " + userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Seller getSellerById(int sellerId) {
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

    public Seller createSeller(Seller seller, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "User", "Id", userId));
        seller.setUser(user);
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        return sellerRepository.save(seller);
    }

    @Transactional
    @Override

    public Seller updateSeller(int sellerId, int userId, Seller sellerRequest) {
        if(!userRepository.existsById(userId))
            throw  new ResourceNotFoundException("User", "Id", userId);
        return sellerRepository.findById(sellerId).map(seller -> {
            seller.setUsername(sellerRequest.getUsername());
            seller.setPassword(sellerRequest.getPassword());
            seller.setEmail(sellerRequest.getEmail());
            seller.setStoreAdress(sellerRequest.getStoreAdress());
            seller.setBusinessName(sellerRequest.getBusinessName());
            return sellerRepository.save(seller);
        }).orElseThrow(() -> new ResourceNotFoundException(
                        "Seller", "Id", sellerId));
    }

    @Transactional
    @Override

    public ResponseEntity<?> deleteSeller(int sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seller not found with Id " + sellerId));
        sellerRepository.delete(seller);
        return ResponseEntity.ok().build();
    }

	@Override
	public Seller getSellerByUsername(String username) {
		return sellerRepository.findByUsername(username);
	}

	@Override
	public void UpdateResetPasswordToken(String token, String email) throws ResourceNotFoundException {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller != null) {
            System.out.println(seller);
        	seller.setResetPasswordToken(token);
            sellerRepository.save(seller);
            
        } else {
            throw new ResourceNotFoundException("Could not find any customer with the email " + email);
        }
    }

	@Override
	public Seller getByResetPasswordToken(String token) {
		 return sellerRepository.findByResetPasswordToken(token);
	}

	@Override
	public void UpdatePassword(Seller seller, String newPassword) {
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        seller.setPassword(encodedPassword);
	         
	        seller.setResetPasswordToken(null);
	        sellerRepository.save(seller);
		
	}


    
}
