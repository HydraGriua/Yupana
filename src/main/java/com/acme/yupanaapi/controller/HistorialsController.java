package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Historial;
import com.acme.yupanaapi.domain.service.HistorialService;
import com.acme.yupanaapi.resource.HistorialResource;
import com.acme.yupanaapi.resource.SaveHistorialResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class HistorialsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private HistorialService historialService;

    @Operation(summary = "Create historial",description = "Create historial for a seller",tags = {"sellers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Create historial for a seller",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/sellers/{sellerId}/historials")
    public HistorialResource createHistorial(@PathVariable(name = "sellerId") Integer sellerId, @Valid @RequestBody SaveHistorialResource resource){
        return convertToResource(historialService.save(convertToEntity(resource), sellerId));
    }

    @Operation(summary = "Delete historial",description = "Delete historial by given Id",tags = {"historials"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete historial by given Id",content =@Content(mediaType = "application/json"))
    })
    @DeleteMapping("/historials/{historialId}")
    public ResponseEntity<?> deleteHistorial(@PathVariable(name="historialId") Integer historialId){
        return historialService.deleteHistorial(historialId);
    }

    @Operation(summary = "Get all historials by seller",description = "Get all historials by seller",tags = {"sellers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all historials by seller",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/sellers/{sellerId}/historials")
    public List<HistorialResource> getAllBySellerId(@PathVariable(name = "sellerId") Integer sellerId) {
        return historialService.getAllHistorialBySellerId(sellerId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Operation(summary = "Get last historial by seller",description = "Get last historial by seller",tags = {"sellers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get last historial by seller",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/sellers/{sellerId}/LastHistorial")
    public HistorialResource getLastHistorialBySellerId(@PathVariable(name = "sellerId") Integer sellerId) {
        return convertToResource(historialService.getLastHistorialBySellerId(sellerId));
    }

    @Operation(summary = "Get historial by Id",description = "Get historial by Id",tags = {"historials"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get historial by Id",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/historials/{historialId}")
    public HistorialResource getHistorialById(@PathVariable(name = "historialId") Integer historialId) {
        return convertToResource(historialService.getHistorialById(historialId));
    }

    private Historial convertToEntity(SaveHistorialResource resource) {
        return mapper.map(resource, Historial.class);
    }
    private HistorialResource convertToResource(Historial entity) {
        return mapper.map(entity, HistorialResource.class);
    }
}

