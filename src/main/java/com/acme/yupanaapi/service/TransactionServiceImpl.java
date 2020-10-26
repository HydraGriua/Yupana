package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.repository.TransactionRepository;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Transaction saveTransaction(Transaction transactionEntity) {
		return transactionRepository.save(transactionEntity);
	}

	@Override
	public Transaction updateTransaction(Transaction transactionEntity, Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Transaction not found with Id " + transactionId));
		transaction.setTransactionName(transactionEntity.getTransactionName());
		transaction.setTransactionDate(transactionEntity.getTransactionDate());
		transaction.setAmount(transactionEntity.getAmount());
		transaction.setInterestRate(transactionEntity.getInterestRate());
		transaction.setCapitalization(transactionEntity.getCapitalization());
		transaction.setCurrencyType(transactionEntity.getCurrencyType());
		transaction.setRatePeriod(transactionEntity.getRatePeriod());
		transaction.setRateType(transactionEntity.getRateType());
		return transactionRepository.save(transaction);
		//TODO: verificar posible metodo con mapping
	}
	
	@Override
	public ResponseEntity<?> deleteTransaction(Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Transaction not found with Id " + transactionId));
		transactionRepository.delete(transaction);
		return ResponseEntity.ok().build();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Transaction getTransactionById(Long transactionId) {
		return transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Transaction not found with Id " + transactionId));
	}
	
	@Override
	public List<Transaction> getAllByFlowId(Long flowId){
		return transactionRepository.findAllByFlowId(flowId);
	}
}
