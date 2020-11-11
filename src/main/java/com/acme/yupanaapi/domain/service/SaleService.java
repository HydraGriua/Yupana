package com.acme.yupanaapi.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Sale;

public interface SaleService {
	Sale save(Sale saleEntity) ;
	Sale update(Sale saleEntity, Long saleId);
	ResponseEntity<?> deleteSale(Long saleId);
	Sale getSaleById(Long saleId);
	List<Sale> getAllBySaleDate(Date saleDate);
}
