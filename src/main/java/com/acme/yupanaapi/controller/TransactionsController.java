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
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactions by flowId",description = "Get all transactions given flowId",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all transactions given flowId",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/flows/{flowId}/transactions")
    public List<TransactionResource> getAllByFlowId(@PathVariable(name = "flowId") Long flowId) {
        return transactionService.getAllByFlowId(flowId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Operation(summary = "Get transaction by Id",description = "Get transaction given Id",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Get transaction given Id",content =@Content(mediaType = "application/json"))
    })
    @GetMapping("/transactions/{transactionId}")
    public TransactionResource getById(@PathVariable(name = "transactionId") Long transactionId) {
        return convertToResource(transactionService.getTransactionById(transactionId));
    }

    @Operation(summary = "Create transaction",description = "Create transaction for a flow",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Create transaction for a flow",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("flows/{flowId}/transactions")
    public TransactionResource createTransaction(@PathVariable(name = "flowId") Long flowId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.createTransaction(convertToEntity(resource),flowId));
    }

    @Operation(summary = "Update transaction",description = "Update transaction given flowId and saleId",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="Update transaction given flowId and saleId",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("flows/{flowId}/saleId={saleId}/transactions")
    public TransactionResource createTransactionWithSale(@PathVariable(name = "flowId") Long flowId, @PathVariable(name = "saleId") Long saleId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.createTransactionWithSale(convertToEntity(resource),flowId,saleId));
    }

    @Operation(summary = "Delete transaction",description = "Delete transaction given Id",tags = {"transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete transaction given Id",content =@Content(mediaType = "application/json"))
    })
    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(name ="transactionId") Long transactionId){
        return transactionService.deleteTransaction(transactionId);
    }

  //convert 
    private Transaction convertToEntity(SaveTransactionResource resource) {
        return mapper.map(resource, Transaction.class);
    }

    private TransactionResource convertToResource(Transaction entity) {
        return mapper.map(entity, TransactionResource.class);
    }
}
