package com.acme.yupanaapi.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.resource.FlowResource;
import com.acme.yupanaapi.resource.SaveFlowResource;

@RestController
@RequestMapping("/api")
public class FlowsController {
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FlowService flowService;

////////////////////////////////////////////////////////////
//Metodos crud para las llamadas
	
	@GetMapping("/flows/{date}")
	public List<FlowResource> getAllByDeadlineDate(@PathVariable(name = "date") Date date) {
		return flowService.getAllByDeadlineDate(date).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	@GetMapping("/wallets/{walletId}/flows")
	public List<FlowResource> getAllByWalletId(@PathVariable(name = "walletId") Long walletId) {
		return flowService.getAllByWalletId(walletId).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	
	@GetMapping("/flows/{flowId}")
	public FlowResource getFlowById(@PathVariable(name = "flowId") Long flowId) {
		return convertToResource(flowService.getFlowById(flowId));
	}

	@PostMapping("/sellers/{sellerId}/wallets/{walletId}/flows")
	public FlowResource createFlow(@Valid @RequestBody SaveFlowResource resource,
			@PathVariable(name = "walletId") Long walletId,@PathVariable(name = "sellerId") Long sellerId) {
		return convertToResource(flowService.createFlow(convertToEntity(resource), walletId, sellerId));
	}
	
	@PutMapping("/sellers/{sellerId}/wallets/{walletId}/flows/{flowId}")
	public FlowResource updateFlow(@PathVariable(name = "flowId") Long flowId,@PathVariable(name = "walletId") Long walletId,
			@PathVariable(name = "sellerId") Long sellerId,	@Valid @RequestBody SaveFlowResource resource) {
		return convertToResource(flowService.updateFlow(flowId, walletId, sellerId, convertToEntity(resource)));
	}

	@DeleteMapping("/flows/{flowId}")
	public ResponseEntity<?> deleteFlow(@PathVariable(name = "flowId") Long flowId) {
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
