package com.acme.yupanaapi.domain.service;


import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Monedero;



public interface MonederoService{
	Monedero save(Monedero entity);
	Monedero update(Monedero entity, Long monederoId);
	ResponseEntity<?> deleteEntity(Long monederoId);
	Monedero getMonederoById(Long monederoId);
}
