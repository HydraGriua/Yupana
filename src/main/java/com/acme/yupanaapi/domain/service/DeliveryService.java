package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.acme.yupanaapi.domain.model.Delivery;


public interface DeliveryService{
	Delivery save(Delivery deliveryEntity) throws Exception;
	Delivery update(Delivery deliveryEntity, Long Id);
	void delete(Long DeliveryId) throws Exception;
	List<Delivery> findAll() throws Exception;
	Optional<Delivery> findById(Long DeliveryId) throws Exception;
	List<Delivery> findAllByDeliveryDate(Date deliveryDate) throws Exception;

	//TODO: agregar el nombre de la entidad luego del metodo
}
