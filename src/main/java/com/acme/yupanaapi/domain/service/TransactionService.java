package com.acme.yupanaapi.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionService {
	Transaction saveTransaction(Transaction transactionEntity);
	Transaction updateTransaction(Transaction transactionEntity, Long transactionId);
	ResponseEntity<?> deleteTransaction(Long transactionId);
	Transaction getTransactionById(Long transactionId);
	List<Transaction> getAllByFlowId(Long flowId);
}
