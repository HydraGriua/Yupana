package com.acme.services;

import org.springframework.http.ResponseEntity;

import com.acme.entities.Suscripcion;



public interface SuscripcionService {
	Suscripcion save(Suscripcion suscripcionEntity);
	Suscripcion update(Suscripcion suscripcionEntity, Long suscbripcionId);
	ResponseEntity<?> deleteEntity(Long suscbripcionId);
	Suscripcion getSuscripcionById(Long suscbripcionId);
}
