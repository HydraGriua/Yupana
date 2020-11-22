package com.acme.yupanaapi.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionService {
	Transaction createTransaction(Transaction transactionEntity, Integer flowId);
	Transaction AssignTransactionWithSale(Integer transactionId, Integer saleId);
	Transaction updateTransaction(Transaction transactionEntity, Integer transactionId);
	ResponseEntity<?> deleteTransaction(Integer transactionId);
	Transaction getTransactionById(Integer transactionId);
	List<Transaction> getAllByFlowId(Integer flowId);
	List<Transaction> getAllByHistorialId(Integer historialId);
}
