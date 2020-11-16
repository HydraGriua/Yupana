package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.service.SaleService;
import com.acme.yupanaapi.resource.FlowResource;
import com.acme.yupanaapi.resource.SaleResource;
import com.acme.yupanaapi.resource.SaveSaleResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalesController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SaleService saleService;

    @GetMapping("/sales/{saleId}")
    public SaleResource getSaleById(@PathVariable(name = "saleId") Long saleId){
        return convertToResource(saleService.getSaleById(saleId));
    }

    @PostMapping("/sales")
    public SaleResource createSale(@Valid @RequestBody SaveSaleResource resource){
        return convertToResource(saleService.save(convertToEntity(resource)));
    }

    @PutMapping("/sales/{saleId}")
    public SaleResource updateSale(@PathVariable(name = "saleId") Long saleId, @Valid @RequestBody SaveSaleResource resource){
        return convertToResource(saleService.update(convertToEntity(resource),saleId));
    }

    @DeleteMapping("/sale/{saleId}")
    public ResponseEntity<?> deleteSale(@PathVariable(name="saleId") Long saleId){
        return saleService.deleteSale(saleId);
    }

    @GetMapping("/sales/Date={saleDate}")
    public List<SaleResource> getAllBySaleDate(@PathVariable(name = "saleDate") Date saleDate) {
        return saleService.getAllBySaleDate(saleDate).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    private Sale convertToEntity(SaveSaleResource resource) {
        return mapper.map(resource, Sale.class);
    }

    private SaleResource convertToResource(Sale entity) {
        return mapper.map(entity, SaleResource.class);
    }
}
