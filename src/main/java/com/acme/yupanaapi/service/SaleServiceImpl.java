package com.acme.yupanaapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.repository.SaleRepository;
import com.acme.yupanaapi.domain.service.SaleService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

@Service
public class SaleServiceImpl implements SaleService{

	@Autowired
	private SaleRepository saleRepository;
	
	@Override
	public Sale save(Sale saleEntity) {
		return saleRepository.save(saleEntity);
	}

	@Override
	public Sale update(Sale saleEntity, Long saleId) {
		 Sale sale = saleRepository.findById(saleId)
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Sale not found with Id " + saleId));
		 sale.setDescription(saleEntity.getDescription());
		 sale.setNetAmount(saleEntity.getNetAmount());
		 sale.setPaymentPay(saleEntity.getPaymentPay());
		 sale.setSaleDate(saleEntity.getSaleDate());
		 sale.setSalePaid(saleEntity.getSalePaid());
		 sale.setSaleTime(saleEntity.getSaleTime());
		 return saleRepository.save(sale);
		//TODO: verificar posible metodo con mapping
	}

	@Override
	public ResponseEntity<?> deleteUser(Long saleId) {
		Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sale not found with Id " + saleId));
        saleRepository.delete(sale);
        return ResponseEntity.ok().build();
	}

	@Transactional(readOnly =true)
	@Override
	public Sale getSaleById(Long saleId) {
		return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sale not found with Id " + saleId));
	}

	@Transactional(readOnly =true)
	@Override
	public List<Sale> getAllBySaleDate(Date saleDate) {
		 return saleRepository.findAllBySaleDate(saleDate);
	}

}
