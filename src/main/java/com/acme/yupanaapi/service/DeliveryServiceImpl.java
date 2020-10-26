package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.repository.DeliveryRepository;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Override
	public Delivery saveDelivery(Delivery deliveryEntity) {
		return deliveryRepository.save(deliveryEntity);
	}

	@Override
	public Delivery updateDelivery(Delivery deliveryEntity, Long deliveryId) {
		 Delivery delivery = deliveryRepository.findById(deliveryId)
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Delivery not found with Id " + deliveryId));
		 delivery.setDeliveryDate(deliveryEntity.getDeliveryDate());
		 delivery.setDeliveryHour(deliveryEntity.getDeliveryHour());
		 delivery.setDeliveryPrice(deliveryEntity.getDeliveryPrice());
		 delivery.setDescription(deliveryEntity.getDescription());
		 delivery.setDirection(deliveryEntity.getDescription());
		 return deliveryRepository.save(delivery);
	        //TODO: verificar posible metodo con mapping
	}

	@Override
	public ResponseEntity<?> deleteDelivery(Long deliveryId) {
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
	public Delivery getDeliveryById(Long deliveryId) {
		return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery not found with Id " + deliveryId));
	}

}
