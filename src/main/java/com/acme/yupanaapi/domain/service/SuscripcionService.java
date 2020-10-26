package com.acme.yupanaapi.domain.service;

import org.springframework.http.ResponseEntity;

import com.acme.yupanaapi.domain.model.Suscripcion;




public interface SuscripcionService {
	Suscripcion save(Suscripcion suscripcionEntity);
	Suscripcion update(Suscripcion suscripcionEntity, Long suscbripcionId);
	ResponseEntity<?> deleteEntity(Long suscbripcionId);
	Suscripcion getSuscripcionById(Long suscbripcionId);
}
