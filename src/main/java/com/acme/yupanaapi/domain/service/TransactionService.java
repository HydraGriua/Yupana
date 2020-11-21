package com.acme.yupanaapi.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionService {
	Transaction createTransaction(Transaction transactionEntity, int flowId);
	Transaction AssignTransactionWithSale(int transactionId, int saleId);
	Transaction updateTransaction(Transaction transactionEntity, int transactionId);
	ResponseEntity<?> deleteTransaction(int transactionId);
	Transaction getTransactionById(int transactionId);
	List<Transaction> getAllByFlowId(int flowId);
	List<Transaction> getAllByHistorialId(int historialId);
}
