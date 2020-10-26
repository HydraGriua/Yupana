package com.acme.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.entities.Suscripcion;
import com.acme.repositories.SuscripcionRepository;
import com.acme.services.SuscripcionService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;



@Service
public class SuscripcionServiceImpl implements SuscripcionService{


	@Autowired
	private SuscripcionRepository suscripcionRepository;

	@Override
	public Suscripcion save(Suscripcion suscripcionEntity) {
		return suscripcionRepository.save(suscripcionEntity);
		
	}

	@Override
	public Suscripcion update(Suscripcion suscripcionEntity, Long suscbripcionId) {
		Suscripcion suscripcion = suscripcionRepository.findById(suscbripcionId).orElseThrow(()-> new ResourceNotFoundException("Subscription not found with Id"+ suscbripcionId));
		suscripcion.setFechaEmision(suscripcionEntity.getFechaEmision());
		suscripcion.setFechaVencimiento(suscripcionEntity.getFechaVencimiento());
		suscripcion.setMonto(suscripcionEntity.getMonto());
		suscripcion.setTipoSuscripcion(suscripcionEntity.getTipoSuscripcion());
		return suscripcionRepository.save(suscripcion);
	}

	@Override
	public ResponseEntity<?> deleteEntity(Long suscbripcionId) {
		Suscripcion suscripcion = suscripcionRepository.findById(suscbripcionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + suscbripcionId));
		suscripcionRepository.delete(suscripcion);
        return ResponseEntity.ok().build();
		
	}

	@Override
	public Suscripcion getSuscripcionById(Long suscbripcionId) {
		return suscripcionRepository.findById(suscbripcionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + suscbripcionId));
	}

	


}
