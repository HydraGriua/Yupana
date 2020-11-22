package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Delivery;


public interface DeliveryService{
	Delivery saveDelivery(Delivery deliveryEntity);
	Delivery updateDelivery(Delivery deliveryEntity, Integer saleId, Integer deliveryId);
	ResponseEntity<?> deleteDelivery(Integer deliveryId);
	List<Delivery> getAllByDeliveryDate(Date deliveryDate);
	Delivery getDeliveryById(Integer deliveryId);
	Delivery createDelivery(Delivery delivery, Integer saleId);
	List<Delivery> getAllBySaleId(Integer saleId);

}
