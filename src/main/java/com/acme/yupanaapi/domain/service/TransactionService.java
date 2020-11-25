package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.resource.InfoFilter;

public interface TransactionService {
	Transaction createTransaction(Transaction transactionEntity, int flowId);
	Transaction updateTransaction(Transaction transactionEntity, int transactionId);
	ResponseEntity<?> deleteTransaction(int transactionId);
	Transaction getTransactionById(int transactionId);
	List<Transaction> getAllByFlowId(int flowId);
	List<Transaction> getAllByHistorialId(int historialId);
	List<Transaction> orderByObj(InfoFilter obj, List<Transaction> listAll);
	List<Transaction> getAllByTransactionDate(Date transactionDate);
}
