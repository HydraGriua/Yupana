package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Historial;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HistorialService {

    Historial save(Historial historialEntity, int sellerId) ;
    ResponseEntity<?> deleteHistorial(int historialId);
    Historial getHistorialById(int historialId);
    Historial getLastHistorialBySellerId(int sellerId);
    List<Historial> getAllHistorialBySellerId(int sellerId);
}
