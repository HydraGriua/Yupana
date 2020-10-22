package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Flow;

public interface FlowRepository extends JpaRepository<Flow, Long>{
	
}