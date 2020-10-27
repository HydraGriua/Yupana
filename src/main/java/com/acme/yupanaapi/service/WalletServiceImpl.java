	package com.acme.yupanaapi.service;


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

	@Transactional
	@Override
	public Wallet createWallet(Wallet entity){
		return walletRepository.save(entity);
	}

	@Transactional
	@Override
	public Wallet updateWallet(Wallet entity, Long walletId) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(()-> new ResourceNotFoundException("Wallet not found with Id" + walletId));
		wallet.setMaintenancePrice(entity.getMaintenancePrice());
		wallet.setState(entity.getState());
		wallet.setCreationDate(entity.getCreationDate());
		wallet.setBalance(entity.getBalance());
		wallet.setType(entity.getType());
		wallet.setCurrencyType(entity.getCurrencyType());
		return walletRepository.save(wallet);
		//TODO: verificar posible metodo con mapping
	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteWallet(Long walletId) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(()->new ResourceNotFoundException("Wallet not found with Id" + walletId));
		walletRepository.delete(wallet);
		return ResponseEntity.ok().build();	
	}

	@Transactional(readOnly = true)
	@Override
	public Wallet getWalletById(Long walletId) {
		return walletRepository.findById(walletId)
				.orElseThrow(()-> new ResourceNotFoundException(
						"Wallet not found with Id" + walletId));
	}

	@Transactional(readOnly = true)
	@Override
	public Wallet getWalletByIdAndUserId(Long walletId, Long userId) {
		return walletRepository.findByIdAndUserId(walletId,userId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Wallet not found with Id " + walletId +
								" and UserId " + userId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Wallet> getAllBySellerId(Long sellerId) {
		return walletRepository.findAllBySellerId(sellerId);
	}

	}
