package com.acme.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.entities.Suscripcion;
import com.acme.repositories.SuscripcionRepository;
import com.acme.services.SuscripcionService;



@Service
public class SuscripcionServiceImpl implements SuscripcionService, Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private SuscripcionRepository suscripcionRepository;
	
	@Transactional
	@Override
	public Suscripcion save(Suscripcion entity) throws Exception {
		return suscripcionRepository.save(entity);
	}

	@Transactional
	@Override
	public Suscripcion update(Suscripcion entity) throws Exception {
		return suscripcionRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		suscripcionRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Suscripcion> findAll() throws Exception {
		return suscripcionRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Suscripcion> findById(Integer id) throws Exception {
		return suscripcionRepository.findById(id);
	}
	

}
