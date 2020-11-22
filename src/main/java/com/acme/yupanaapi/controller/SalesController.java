package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Sale;
import com.acme.yupanaapi.domain.service.SaleService;
import com.acme.yupanaapi.resource.SaleResource;
import com.acme.yupanaapi.resource.SaveSaleResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SalesController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SaleService saleService;

    @Operation(summary = "Get Sale",description = "Get Sale by given id",tags = {"sales"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Sale by given id",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/sales/{saleId}")
    public SaleResource getSaleById(@PathVariable(name = "saleId") Integer saleId){
        return convertToResource(saleService.getSaleById(saleId));
    }

    @Operation(summary = "Create Sale",description = "Create Sale",tags = {"sales"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Sale",content =@Content(mediaType = "application/json") )
    })
    @PostMapping("/sales")
    public SaleResource createSale(@Valid @RequestBody SaveSaleResource resource){
        return convertToResource(saleService.save(convertToEntity(resource)));
    }

    @Operation(summary = "Update Sale",description = "Update Sale",tags = {"sales"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Sale",content =@Content(mediaType = "application/json") )
    })
    @PutMapping("/sales/{saleId}")
    public SaleResource updateSale(@PathVariable(name = "saleId") Integer saleId, @Valid @RequestBody SaveSaleResource resource){
        return convertToResource(saleService.update(convertToEntity(resource),saleId));
    }

    @Operation(summary = "Delete Sale",description = "Delete Sale by given id",tags = {"sales"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Sale by given id",content =@Content(mediaType = "application/json") )
    })
    @DeleteMapping("/sale/{saleId}")
    public ResponseEntity<?> deleteSale(@PathVariable(name="saleId") Integer saleId){
        return saleService.deleteSale(saleId);
    }

    @Operation(summary = "Get Sales by date",description = "Get Sales by date",tags = {"sales"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Sales by date",content =@Content(mediaType = "application/json") )
    })
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
