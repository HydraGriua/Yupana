package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.repository.HistorialRepository;
import com.acme.yupanaapi.domain.repository.SellerRepository;
import com.acme.yupanaapi.domain.service.HistorialService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Historial save(Historial historialEntity, Integer sellerId) {
        return sellerRepository.findById(sellerId).map(seller -> {
            historialEntity.setSeller(seller);
            historialEntity.setIdOfSeller(sellerId);
            return historialRepository.save(historialEntity);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Seller not found with Id " + sellerId));
        
    }

    @Override
    public ResponseEntity<?> deleteHistorial(Integer historialId) {
        return historialRepository.findById(historialId).map(historial -> {
            historialRepository.delete(historial);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                        "Historial not found with Id " + historialId));
    }

    @Override
    public Historial getHistorialById(Integer historialId) {
        return historialRepository.findById(historialId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial not found with Id " + historialId));
    }

    @Override
    public Historial getLastHistorialBySellerId(Integer sellerId) {
        List<Historial> lista = historialRepository.findBySellerId(sellerId);
        return lista.get(lista.size()-1);
    }

    @Override
    public List<Historial> getAllHistorialBySellerId(Integer sellerId) {
        return historialRepository.findBySellerId(sellerId);
    }
}
