package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.repository.FlowRepository;
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

	@Autowired
	FlowRepository flowRepository;

	@Transactional
	@Override
	public Transaction createTransaction(Transaction transactionEntity, Long flowId) {
		Flow flow = flowRepository.findById(flowId).orElseThrow(() -> new ResourceNotFoundException(
				"Flow not found with Id " + flowId));
		transactionEntity.setFlow(flow);
		return transactionRepository.save(transactionEntity);
		//TODO: revisar si depende de la venta y posible redifinicion de la BD
	}

	@Transactional
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

	@Transactional
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

	@Transactional(readOnly = true)
	@Override
	public List<Transaction> getAllByFlowId(Long flowId){
		return transactionRepository.findAllByFlowId(flowId);
	}
}
