package com.acme.yupanaapi.domain.repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Delivery;

public interface  DeliveryRepository extends JpaRepository<Delivery, Integer> {
	List<Delivery> findAllByDeliveryDate(Date deliveryDate);
	List<Delivery> findAllBySaleId(Integer saleId);
}

