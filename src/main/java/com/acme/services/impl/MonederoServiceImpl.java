package com.acme.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.entities.Monedero;
import com.acme.repositories.MonederoRepository;
import com.acme.services.MonederoService;



@Service
public class MonederoServiceImpl implements MonederoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MonederoRepository monederoRepository;
	
	@Transactional
	@Override
	public Monedero save(Monedero entity) throws Exception {
		return monederoRepository.save(entity);
	}

	@Transactional
	@Override
	public Monedero update(Monedero entity) throws Exception {
		return monederoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		monederoRepository.deleteById(id);
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<Monedero> findAll() throws Exception {
		return monederoRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Monedero> findById(Integer id) throws Exception {
		return monederoRepository.findById(id);
	}
	
}
