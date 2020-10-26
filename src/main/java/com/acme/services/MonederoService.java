package com.acme.services;


import org.springframework.http.ResponseEntity;

import com.acme.entities.Monedero;

public interface MonederoService{
	Monedero save(Monedero entity);
	Monedero update(Monedero entity, Long monederoId);
	ResponseEntity<?> deleteEntity(Long monederoId);
	Monedero getMonederoById(Long monederoId);
}
