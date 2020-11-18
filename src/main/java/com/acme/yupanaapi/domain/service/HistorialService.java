package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Historial;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HistorialService {
    Historial save(Historial historialEntity, Long sellerId) ;
    ResponseEntity<?> deleteHistorial(Long historialId);
    Historial getHistorialById(Long historialId);
    Historial getLastHistorialBySellerId(Long sellerId);
    List<Historial> getAllHistorialBySellerId(Long sellerId);
}
