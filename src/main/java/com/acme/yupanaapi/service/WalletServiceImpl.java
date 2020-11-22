	package com.acme.yupanaapi.service;


import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.SellerRepository;
import com.acme.yupanaapi.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.repository.WalletRepository;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import java.util.List;


@Service
public class WalletServiceImpl implements WalletService {

	
	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public Wallet createWallet(Wallet entity, int sellerId, int userId){
		Seller seller = sellerRepository.findById(sellerId)
				.orElseThrow(()-> new ResourceNotFoundException("Seller not found with Id" + sellerId));
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User not found with Id" + userId));
		entity.setSeller(seller);
		entity.setUser(user);
		entity.setIdOfUser(user.getId());
		return walletRepository.save(entity);
	}

	@Transactional
	@Override
	public Wallet updateWallet(Wallet entity, int walletId, int sellerId) {
		if(!sellerRepository.existsById(sellerId))
			throw  new ResourceNotFoundException("Seller not found with Id" + sellerId);
		return walletRepository.findById(walletId).map(wallet -> {
			wallet.setMaintenancePrice(entity.getMaintenancePrice());
			wallet.setState(entity.getState());
			wallet.setCreationDate(entity.getCreationDate());
			wallet.setBalance(entity.getBalance());
			wallet.setType(entity.getType());
			wallet.setCurrencyType(entity.getCurrencyType());
			return walletRepository.save(wallet);
		}).orElseThrow(()-> new ResourceNotFoundException("Wallet not found with Id" + walletId));
	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteWallet(int walletId) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(()->new ResourceNotFoundException("Wallet not found with Id" + walletId));
		walletRepository.delete(wallet);
		return ResponseEntity.ok().build();	
	}

	@Transactional(readOnly = true)
	@Override
	public Wallet getWalletById(int walletId) {
		return walletRepository.findById(walletId)
				.orElseThrow(()-> new ResourceNotFoundException(
						"Wallet not found with Id" + walletId));
	}

	@Transactional(readOnly = true)
	@Override
	public Wallet getWalletByIdAndUserId(int walletId, int userId) {
		return walletRepository.findByIdAndUserId(walletId,userId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Wallet not found with Id " + walletId +
								" and UserId " + userId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Wallet> getAllBySellerId(int sellerId) {
		return walletRepository.findAllBySellerId(sellerId);
	}


}
