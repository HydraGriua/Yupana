package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Wallet;

import java.util.List;


public interface WalletRepository extends JpaRepository<Wallet, Long>{
    List<Wallet> findAllBySellerId(Long sellerId);
}
