package com.acme.yupanaapi.domain.repository;

import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.model.Seller;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial,Integer> {

    List<Historial> findBySellerId(int sellerId);

}
