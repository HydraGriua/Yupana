	package com.acme.yupanaapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Monedero;
import com.acme.yupanaapi.domain.repository.MonederoRepository;
import com.acme.yupanaapi.domain.service.MonederoService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;





@Service
public class MonederoServiceImpl implements MonederoService{

	
	@Autowired
	private MonederoRepository monederoRepository;
	
	
	@Override
	public Monedero save(Monedero entity){
		return monederoRepository.save(entity);
	}

	@Override
	public Monedero update(Monedero entity, Long monederoId) {
		Monedero monedero = monederoRepository.findById(monederoId).orElseThrow(()-> new ResourceNotFoundException("Wallet not found with Id" + monederoId));
		monedero.setCostoMantenimiento(entity.getCostoMantenimiento());
		monedero.setEstado(entity.getEstado());
		monedero.setFechaEmision(entity.getFechaEmision());
		monedero.setSaldo(entity.getSaldo());
		monedero.setTipo(entity.getTipo());
		monedero.setTipoMoneda(entity.getTipoMoneda());
		return monederoRepository.save(monedero);
	}
	


	@Override
	public ResponseEntity<?> deleteEntity(Long monederoId) {
		Monedero monedero = monederoRepository.findById(monederoId).orElseThrow(()->new ResourceNotFoundException("Wallet not found with Id" + monederoId));
		monederoRepository.delete(monedero);
		return ResponseEntity.ok().build();	
	}

	@Transactional(readOnly = true)
	@Override
	public Monedero getMonederoById(Long monederoId) {
		return monederoRepository.findById(monederoId).orElseThrow(()-> new ResourceNotFoundException("Wallet not found with Id" + monederoId));
	}
	
}
