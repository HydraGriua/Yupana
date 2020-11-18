package com.acme.yupanaapi.domain.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Transaction;

public interface TransactionService {
	Transaction createTransaction(Transaction transactionEntity, Long flowId);
	Transaction AssignTransactionWithSale(Long transactionId, Long saleId);
	Transaction updateTransaction(Transaction transactionEntity, Long transactionId);
	ResponseEntity<?> deleteTransaction(Long transactionId);
	Transaction getTransactionById(Long transactionId);
	List<Transaction> getAllByFlowId(Long flowId);
	List<Transaction> getAllByHistorialId(Long historialId);
}
