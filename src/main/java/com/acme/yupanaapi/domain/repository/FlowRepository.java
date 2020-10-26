package com.acme.yupanaapi.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Flow;

public interface FlowRepository extends JpaRepository<Flow, Long>{
	List<Flow> findAllByWalletId(Long id);
	List<Flow> findAllByDate(Date date);
}