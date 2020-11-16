package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.resource.SaleResource;
import com.acme.yupanaapi.resource.SaveSaleResource;
import com.acme.yupanaapi.resource.SaveTransactionResource;
import com.acme.yupanaapi.resource.TransactionResource;
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
public class TransactionsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/transactions/{transactionId}")
    public TransactionResource getTransactionById(@PathVariable(name = "transactionId") Long transactionId){
        return convertToResource(transactionService.getTransactionById(transactionId));
    }

    @PostMapping("/flows/{flowId}/transactions")
    public TransactionResource createTransaction(@PathVariable(name = "flowId") Long flowId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.createTransaction(convertToEntity(resource), flowId));
    }

    @PutMapping("/transactions/{transactionId}")
    public TransactionResource updateTransaction(@PathVariable(name = "transactionId") Long transactionId, @Valid @RequestBody SaveTransactionResource resource){
        return convertToResource(transactionService.updateTransaction(convertToEntity(resource),transactionId));
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable(name="transactionId") Long transactionId){
        return transactionService.deleteTransaction(transactionId);
    }

    @GetMapping("/flows/{flowId}/transactions")
    public List<TransactionResource> getAllByFlowId(@PathVariable(name = "flowId") Long flowId) {
        return transactionService.getAllByFlowId(flowId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    private Transaction convertToEntity(SaveTransactionResource resource) {
        return mapper.map(resource, Transaction.class);
    }

    private TransactionResource convertToResource(Transaction entity) {
        return mapper.map(entity, TransactionResource.class);
    }
}
