package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Historial;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HistorialService {
    Historial save(Historial historialEntity, Integer sellerId) ;
    ResponseEntity<?> deleteHistorial(Integer historialId);
    Historial getHistorialById(Integer historialId);
    Historial getLastHistorialBySellerId(Integer sellerId);
    List<Historial> getAllHistorialBySellerId(Integer sellerId);
}
