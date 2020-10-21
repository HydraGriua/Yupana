package com.acme.yupanaapi.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.repository.DeliveryRepository;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Transactional
	@Override
	public Delivery save(Delivery deliveryEntity) throws Exception {
		return deliveryRepository.save(deliveryEntity);
	}

	@Override
	public Delivery update(Delivery deliveryEntity, Long DeliveryId){
		Delivery delivery = deliveryRepository.findById(DeliveryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery not found with Id " + DeliveryId));
		delivery.setDeliveryDate(deliveryEntity.getDeliveryDate());
		delivery.setDeliveryHour(deliveryEntity.getDeliveryHour());
		delivery.setDeliveryPrice(deliveryEntity.getDeliveryPrice());
		delivery.setDescription(deliveryEntity.getDescription());
		delivery.setDirection(deliveryEntity.getDirection());
        return deliveryRepository.save(delivery);
	}

	@Override
	public void deleteById(Long deliveryId) throws Exception {
		deliveryRepository.deleteById(deliveryId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Delivery> findAll() throws Exception {
		return deliveryRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Delivery> findById(Long deliveryId) throws Exception{
		return deliveryRepository.findById(deliveryId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Delivery> findByDeliveryDate(Date deliveryDate) throws Exception{
		return deliveryRepository.findByDeliveryDate(deliveryDate);
	}

}
