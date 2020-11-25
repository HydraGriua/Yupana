package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Delivery;


public interface DeliveryService{
	Delivery updateDelivery(Delivery deliveryEntity, int transactionId, int deliveryId);
	ResponseEntity<?> deleteDelivery(int deliveryId);
	List<Delivery> getAllByDeliveryDate(Date deliveryDate);
	Delivery getDeliveryById(int deliveryId);
	Delivery createDelivery(Delivery delivery, int transactionId);
	List<Delivery> getAllByTransactionId(int transactionId);
}
