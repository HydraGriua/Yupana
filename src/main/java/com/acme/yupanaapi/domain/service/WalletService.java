package com.acme.yupanaapi.domain.service;


import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Wallet;

import java.util.List;


public interface WalletService {
	Wallet createWallet(Wallet entity, int sellerId, int userId);
	Wallet updateWallet(Wallet entity, int walletId, int sellerId);
	ResponseEntity<?> deleteWallet(int walletId);
	Wallet getWalletById(int walletId);
	Wallet getWalletByIdAndUserId(int walletId, int userId);
	List<Wallet> getAllBySellerId(int sellerId);	

}
