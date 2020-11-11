package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;

import com.acme.yupanaapi.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.repository.DeliveryRepository;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	SaleRepository saleRepository;

	@Transactional
	@Override
	public Delivery saveDelivery(Delivery deliveryEntity) {
		return deliveryRepository.save(deliveryEntity);
	}

	@Transactional
	@Override
	public Delivery createDelivery(Delivery delivery, Long saleId) {
		Sale sale = saleRepository.findById(saleId)
				.orElseThrow(()-> new ResourceNotFoundException("sale not found with Id " + saleId));
		delivery.setSale(sale);
		return deliveryRepository.save(delivery);
	}

	@Transactional
	@Override
	public Delivery updateDelivery(Delivery deliveryEntity, Long saleId, Long deliveryId) {
		if (!saleRepository.existsById(saleId))
			throw new ResourceNotFoundException("Sale", "Id", saleId);
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

	@Transactional(readOnly = true)
	@Override
	public List<Delivery> getAllBySaleId(Long saleId) {
		return deliveryRepository.findAllBySaleId(saleId);
	}

}
