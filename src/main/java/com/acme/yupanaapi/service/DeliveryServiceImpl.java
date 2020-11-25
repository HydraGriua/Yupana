package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;

import com.acme.yupanaapi.domain.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.repository.DeliveryRepository;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	TransactionRepository transactionRepository;


	@Transactional
	@Override
	public Delivery createDelivery(Delivery delivery, int transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(()-> new ResourceNotFoundException("Transaction not found with Id " + transactionId));
		delivery.setTransaction(transaction);
		return deliveryRepository.save(delivery);
	}

	@Transactional
	@Override
	public Delivery updateDelivery(Delivery deliveryEntity, int transactionId, int deliveryId) {
		if (!transactionRepository.existsById(transactionId))
			throw new ResourceNotFoundException("Transaction", "Id", transactionId);
		return deliveryRepository.findById(deliveryId).map(delivery -> {
			delivery.setDeliveryDate(deliveryEntity.getDeliveryDate());
			delivery.setDeliveryHour(deliveryEntity.getDeliveryHour());
			delivery.setDeliveryPrice(deliveryEntity.getDeliveryPrice());
			delivery.setDescription(deliveryEntity.getDescription());
			delivery.setDirection(deliveryEntity.getDescription());
			return deliveryRepository.save(delivery);
		}).orElseThrow(() -> new ResourceNotFoundException("Delivery not found with Id " + deliveryId));
	}

	@Transactional
	@Override

	public ResponseEntity<?> deleteDelivery(int deliveryId) {
		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Delivery not found with Id " + deliveryId));
		deliveryRepository.delete(delivery);
		return ResponseEntity.ok().build();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Delivery> getAllByDeliveryDate(Date deliveryDate) {
		return deliveryRepository.findAllByDeliveryDate(deliveryDate);
	}

	@Transactional(readOnly = true)
	@Override

	public Delivery getDeliveryById(int deliveryId) {
		return deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Delivery not found with Id " + deliveryId));
	}

	@Transactional(readOnly = true)
	@Override

	public List<Delivery> getAllByTransactionId(int transactionId) {
		return deliveryRepository.findAllByTransactionId(transactionId);
	}

}
