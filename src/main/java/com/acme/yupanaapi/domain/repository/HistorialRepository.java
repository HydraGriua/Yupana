package com.acme.yupanaapi.domain.repository;

import com.acme.yupanaapi.domain.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialRepository extends JpaRepository<Historial,Long> {
    List<Historial> findBySellerId(Long sellerId);
}
