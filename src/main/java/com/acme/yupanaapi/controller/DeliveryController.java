package com.acme.yupanaapi.controller;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.service.DeliveryService;
import com.acme.yupanaapi.resource.DeliveryResource;
import com.acme.yupanaapi.resource.SaveDeliveryResource;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@RestController
@CrossOrigin
@RequestMapping("/api")
public class DeliveryController {
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private DeliveryService deliveryService;
	
	//obtener delivery por venta
	@Operation(summary = "Get Deliveries by Id", description = "Get Deliveries by Id", tags = {"deliveries"} )
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Deliveries by Id",content =@Content(mediaType = "application/json"))
    })
	@GetMapping("/sales/{saleId}/deliveries")
	public List<DeliveryResource> getDeliveruesBySaleID(@PathVariable(name = "saleId") Integer saleId){
		return deliveryService.getAllBySaleId(saleId).stream().map(this::convertToResource).collect(Collectors.toList());
		
	}
	
	//obtener delivery por fecha
	@Operation(summary = "Get Deliveries by Date", description = "Get Deliveries by Date", tags = {"deliveries"} )
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Deliveries by Date",content =@Content(mediaType = "application/json"))
    })
	@GetMapping("/deliveries/Date={deliveryDate}")
	public List<DeliveryResource> getAllDeliveriesByDate (@PathVariable(name = "deliveryDate") Date deliveryDate){
		
		return deliveryService.getAllByDeliveryDate(deliveryDate).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	
	//crear
	@Operation(summary = "Create deliveries", description = "Create deliveries for a Sale", tags = {"deliveries"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Create deliveries for a Sale", content =@Content(mediaType = "application/json"))
	})
	@PostMapping("/sales/{saleId}/deliveries")
	public DeliveryResource createDelivery (@PathVariable(name = "saleId") Integer saleId, @Valid @RequestBody SaveDeliveryResource resource){
		return convertToResource(deliveryService.createDelivery(convertToEntity(resource), saleId));
	}
	
	//actualizar
	@Operation(summary = "Update Delivery", description = "Update Delivery By Id and Sale Id", tags = {"deliveries"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update Delivery By Id and Sale Id", content =@Content(mediaType = "application/json"))
	})
	@PutMapping("/sales/{saleId}/deliveries/{deliveryId}")
	public DeliveryResource updateDelivery(@PathVariable(name = "saleId") Integer saleId, @PathVariable(name = "deliveryId") Integer deliveryId,@Valid @RequestBody SaveDeliveryResource resource) {
		return convertToResource(deliveryService.updateDelivery(convertToEntity(resource), saleId, deliveryId));
	}
	
	//borrar
	@Operation(summary = "Delete Delivery", description = "Delete Delivery By Id", tags = {"deliveries"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete Delivery By Id", content =@Content(mediaType = "application/json"))
	})
	@DeleteMapping("/deliveries/{deliveryId}")
	public ResponseEntity<?> deleteDelivery(@PathVariable(name = "deliveryId") Integer deliveryId){
		return deliveryService.deleteDelivery(deliveryId);
	}
	
	//convert 
    private Delivery convertToEntity(SaveDeliveryResource resource) {
        return mapper.map(resource, Delivery.class);
    }

    private DeliveryResource convertToResource(Delivery entity) {
        return mapper.map(entity, DeliveryResource.class);
    }
	
}
