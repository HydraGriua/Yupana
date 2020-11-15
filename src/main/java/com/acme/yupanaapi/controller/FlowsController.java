package com.acme.yupanaapi.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.resource.FlowResource;
import com.acme.yupanaapi.resource.SaveFlowResource;

public class FlowsController {
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FlowService flowService;

////////////////////////////////////////////////////////////
//Metodos crud para las llamadas
	
	@GetMapping("/wallet/flows/{id}")
	public List<FlowResource> getAllByDeadlineDate(@PathVariable(name = "id") Date date) {
		return flowService.getAllByDeadlineDate(date).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	@GetMapping("/wallet/flows/{id}")
	public List<FlowResource> getAllByWalletId(@PathVariable(name = "id") Long walletId) {
		return flowService.getAllByWalletId(walletId).stream().map(this::convertToResource).collect(Collectors.toList());
	}
//	@GetMapping("/wallet/flows/{id}")
//	public List<TransactionResource> .getAllTransactionByFlowId(@PathVariable(name = "id") Long flowId) {
//		return flowService.getAllTransactionByFlowId(flowId).stream().map(this::convertToResource).collect(Collectors.toList());
//	}
	
	@GetMapping("/wallet/flows/flowId={flowId}")
	public FlowResource getFlowById(@PathVariable(name = "dni") Long flowId) {
		return convertToResource(flowService.getFlowById(flowId));
	}

	@PostMapping("/wallet/flows")
	public FlowResource createFlow(@Valid @RequestBody SaveFlowResource resource,
			@PathVariable(name = "walletId") Long walletId,@PathVariable(name = "sellerId") Long sellerId) {
		return convertToResource(flowService.createFlow(convertToEntity(resource), walletId, sellerId));
	}
	
	@PutMapping("/wallet/flows/{id}")
	public FlowResource updateFlow(@PathVariable(name = "id") Long flowId,@PathVariable(name = "walletId") Long walletId, 
			@PathVariable(name = "sellerId") Long sellerId,	@Valid @RequestBody SaveFlowResource resource) {
		return convertToResource(flowService.updateFlow(flowId, walletId, sellerId, convertToEntity(resource)));
	}

	@DeleteMapping("/wallet/flows/{id}")
	public ResponseEntity<?> deleteFlow(@PathVariable(name = "id") Long flowId) {
		return flowService.deleteFlow(flowId);
	}

/////////////////////////////////////////////////////////////////////////

	private Flow convertToEntity(SaveFlowResource resource) {
		return mapper.map(resource, Flow.class);
	}

	private FlowResource convertToResource(Flow entity) {
		return mapper.map(entity, FlowResource.class);
	}

}
