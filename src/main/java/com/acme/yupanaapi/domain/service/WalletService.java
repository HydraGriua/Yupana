package com.acme.yupanaapi.domain.service;


import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Wallet;

import java.util.List;


public interface WalletService {
	Wallet createWallet(Wallet entity, Integer sellerId, Integer userId);
	Wallet updateWallet(Wallet entity, Integer walletId, Integer sellerId);
	ResponseEntity<?> deleteWallet(Integer walletId);
	Wallet getWalletById(Integer walletId);
	Wallet getWalletByIdAndUserId(Integer walletId, Integer userId);
	List<Wallet> getAllBySellerId(Integer sellerId);
}
