package com.acme.yupanaapi.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Delivery;


public interface  DeliveryRepository extends JpaRepository<Delivery, Long> {
		Optional<Delivery> findById(Long id) ;
		List<Delivery> findByDeliveryDate(Date deliveryDate);
}

