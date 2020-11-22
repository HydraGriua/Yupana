package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.resource.SaveTransactionResource;
import com.acme.yupanaapi.resource.TransactionResource;

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
public class TransactionsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TransactionService transactionService;


    @Operation(summary = "Get transaction by Id",description = "Get transaction given Id",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Get transaction given Id",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/transactions/{transactionId}")
    public TransactionResource getTransactionById(@PathVariable(name = "transactionId") Integer transactionId){
        return convertToResource(transactionService.getTransactionById(transactionId));
    }

    @Operation(summary = "Create transaction",description = "Create transaction for a flow",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Create transaction for a flow",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("/flows/{flowId}/transactions")
    public TransactionResource createTransaction(@PathVariable(name = "flowId") Integer flowId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.createTransaction(convertToEntity(resource), flowId));
    }

    @Operation(summary = "Update transaction",description = "Update transaction",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Update transaction",content =@Content(mediaType = "application/json"))
    })
    @PutMapping("/transactions/{transactionId}")
    public TransactionResource updateTransaction(@PathVariable(name = "transactionId") Integer transactionId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.updateTransaction(convertToEntity(resource),transactionId));
    }

    @Operation(summary = "Delete transaction",description = "Delete transaction given Id",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete transaction given Id",content =@Content(mediaType = "application/json"))
    })
    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(name="transactionId") Integer transactionId){
        return transactionService.deleteTransaction(transactionId);
    }

    @Operation(summary = "Get all transactions by flowId",description = "Get all transactions given flowId",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all transactions given flowId",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/flows/{flowId}/transactions")
    public List<TransactionResource> getAllByFlowId(@PathVariable(name = "flowId") Integer flowId) {
        return transactionService.getAllByFlowId(flowId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Operation(summary = "Get all transactions by historialId",description = "Get all transactions by historialId",tags = {"historials"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all transactions by historialId",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/historials/{historialId}/transactions")
    public List<TransactionResource> getAllByHistorialId(@PathVariable(name = "historialId") Integer historialId) {
        return transactionService.getAllByHistorialId(historialId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Operation(summary = "Assign transaction with Sale",description = "Assign transaction given Id and saleId",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Assign transaction given Id and saleId",content =@Content(mediaType = "application/json"))
    })
    @PutMapping("transactions/{transactionId}/saleId={saleId}")
    public TransactionResource AssignTransactionWithSale(@PathVariable(name = "transactionId") Integer transactionId, @PathVariable(name = "saleId") Integer saleId){
        return convertToResource(transactionService.AssignTransactionWithSale(transactionId,saleId));
    }

    private Transaction convertToEntity(SaveTransactionResource resource) {
        return mapper.map(resource, Transaction.class);
    }

    private TransactionResource convertToResource(Transaction entity) {
        return mapper.map(entity, TransactionResource.class);
    }
}
