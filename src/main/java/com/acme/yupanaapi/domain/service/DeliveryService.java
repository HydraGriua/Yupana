package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Delivery;


public interface DeliveryService{
	Delivery saveDelivery(Delivery deliveryEntity);
	Delivery updateDelivery(Delivery deliveryEntity, Long saleId, Long deliveryId);
	ResponseEntity<?> deleteDelivery(Long deliveryId);
	List<Delivery> getAllByDeliveryDate(Date deliveryDate);
	Delivery getDeliveryById(Long deliveryId);

}
