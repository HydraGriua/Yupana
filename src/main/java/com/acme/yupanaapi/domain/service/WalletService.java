package com.acme.yupanaapi.domain.service;


import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Wallet;

import java.util.List;


public interface WalletService {
	Wallet createWallet(Wallet entity);
	Wallet updateWallet(Wallet entity, Long walletId);
	ResponseEntity<?> deleteWallet(Long walletId);
	Wallet getWalletById(Long walletId);
	Wallet getWalletByIdAndUserId(Long walletId, Long userId);
	List<Wallet> getAllBySellerId(Long sellerId);
}
