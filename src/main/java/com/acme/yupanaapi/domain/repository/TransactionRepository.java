package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
}
