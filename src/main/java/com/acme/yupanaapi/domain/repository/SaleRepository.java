package com.acme.yupanaapi.domain.repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Integer>{
	List<Sale> findAllBySaleDate(Date saleDate);
}
