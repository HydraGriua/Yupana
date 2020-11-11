package com.acme.yupanaapi.service;

import java.util.List;
import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.repository.FlowRepository;
import com.acme.yupanaapi.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.repository.TransactionRepository;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.exception.LockedActionException;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	FlowRepository flowRepository;

	@Autowired
	SaleRepository saleRepository;

	@Transactional
	@Override
	public Transaction createTransaction(Transaction transactionEntity, Long flowId) {
		// Obtenemos el flow al que pertenecera
		Flow flow = flowRepository.findById(flowId)
				.orElseThrow(() -> new ResourceNotFoundException("Flow not found with Id " + flowId));
		if (flow.getCurrentCreditLine() - transactionEntity.getAmount() >= 0) {
			// Actualizamos los datos del flow
			String tipo = flow.getCurrentRateType();
			Float newQuant;
			int dias = (int) ((transactionEntity.getTransactionDate().getTime()
					- flow.getLastTransactionDate().getTime()) / 86400000);
			switch (tipo) {
			case "simple":
				newQuant = flow.getTotalDebt() * (1 + (flow.getCurrentInterestRate() * dias));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
				break;
			case "Nominal":
				newQuant = flow.getTotalDebt() * (float) Math.pow(
						(1 + (flow.getCurrentInterestRate()
								/ ((float) flow.getCurrentRatePeriod() / flow.getCurrentCapitalization()))),
						((float) dias / flow.getCurrentCapitalization()));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
				break;
			case "Efectiva":
				newQuant = flow.getTotalDebt() * (float) Math.pow((1 + flow.getCurrentInterestRate()),
						(float) (dias / flow.getCurrentRatePeriod()));
				flow.setTotalDebt(newQuant + transactionEntity.getAmount());
			}
			flow.setCurrentInterestRate(transactionEntity.getInterestRate());
			flow.setCurrentRatePeriod(transactionEntity.getRatePeriod());
			flow.setCurrentRateType(transactionEntity.getRateType());
			flow.setCurrentCapitalization(transactionEntity.getCapitalization());
			flow.setCreditLine(flow.getCurrentCreditLine() - transactionEntity.getAmount());
			flowRepository.save(flow);
			// guardamos la transaccion
			transactionEntity.setFlow(flow);

			// if(transactionEntity.getSale() != null)
			// TODO: agregar la venta en caso exista
			return transactionRepository.save(transactionEntity);
		}
		else {
			throw new LockedActionException("Superaste tu linea de credito");
		}
	}

	@Override
	public Transaction createTransactionWithSale(Transaction transactionEntity, Long flowId, Long saleId) {
		Sale sale = saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Sale not found with Id " + saleId));
		transactionEntity.setSale(sale);
		return createTransaction(transactionEntity,flowId);
	}

	@Transactional
	@Override
	public Transaction updateTransaction(Transaction transactionEntity, Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
		transaction.setTransactionName(transactionEntity.getTransactionName());
		transaction.setTransactionDate(transactionEntity.getTransactionDate());
		transaction.setAmount(transactionEntity.getAmount());
		transaction.setInterestRate(transactionEntity.getInterestRate());
		transaction.setCapitalization(transactionEntity.getCapitalization());
		transaction.setCurrencyType(transactionEntity.getCurrencyType());
		transaction.setRatePeriod(transactionEntity.getRatePeriod());
		transaction.setRateType(transactionEntity.getRateType());
		return transactionRepository.save(transaction);
		// TODO: verificar posible metodo con mapping
	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteTransaction(Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
		transactionRepository.delete(transaction);
		return ResponseEntity.ok().build();
	}

	@Transactional(readOnly = true)
	@Override
	public Transaction getTransactionById(Long transactionId) {
		return transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Transaction> getAllByFlowId(Long flowId) {
		return transactionRepository.findAllByFlowId(flowId);
	}
}
