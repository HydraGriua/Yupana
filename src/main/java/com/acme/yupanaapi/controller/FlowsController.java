package com.acme.yupanaapi.controller;
import java.util.Date;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FlowsController {
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FlowService flowService;

////////////////////////////////////////////////////////////
//Metodos crud para las llamadas
	
	@Operation(summary = "Get all flows by dead line date", description ="Get all flows given dead line date", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get all flows given dead line date", content =@Content(mediaType = "application/json") )
	})
	@GetMapping("/wallets/{walletId}/flows/DeadLineDate={date}")
	public List<FlowResource> getAllByDeadlineDate(@PathVariable(name = "walletId") Long walletId,@PathVariable(name = "date") Date date) {
		return flowService.getAllByWalletIdAndDeadlineDate(walletId,date).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	@Operation(summary = "Get all flows by walletId", description ="Get all flows given walletId", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get all flows given walletId", content =@Content(mediaType = "application/json") )
	})
	@GetMapping("/wallets/{walletId}/flows")
	public List<FlowResource> getAllByWalletId(@PathVariable(name = "walletId") Long walletId) {
		return flowService.getAllByWalletId(walletId).stream().map(this::convertToResource).collect(Collectors.toList());
	}
	
	@Operation(summary = "Get flow by Id", description ="Get flow given Id", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Get flow given Id", content =@Content(mediaType = "application/json") )
	})
	@GetMapping("/flows/{flowId}")
	public FlowResource getFlowById(@PathVariable(name = "flowId") Long flowId) {
		return convertToResource(flowService.getFlowById(flowId));
	}

	@Operation(summary = "Create flow", description ="Create flow for a Seller and Wallet", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Create flow for a Seller  and Wallet", content =@Content(mediaType = "application/json") )
	})
	@PostMapping("/sellers/{sellerId}/wallets/{walletId}/flows")
	public FlowResource createFlow(@Valid @RequestBody SaveFlowResource resource,
			@PathVariable(name = "walletId") Long walletId, @PathVariable(name = "sellerId") Long sellerId) {
		return convertToResource(flowService.createFlow(convertToEntity(resource), walletId, sellerId));
	}
	
	@Operation(summary = "Update flow", description ="Update flow given SellerId, WalletId and flowId", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Update flow given SellerId, WalletId and flowId", content =@Content(mediaType = "application/json") )
	})
	@PutMapping("/sellers/{sellerId}/wallets/{walletId}/flows/{flowId}")
	public FlowResource updateFlow(@PathVariable(name = "flowId") Long flowId,@PathVariable(name = "walletId") Long walletId,
			@PathVariable(name = "sellerId") Long sellerId,	@Valid @RequestBody SaveFlowResource resource) {
		return convertToResource(flowService.updateFlow(flowId, walletId, sellerId, convertToEntity(resource)));
	}

	@Operation(summary = "Delete flow", description ="Delete flow given flowId", tags = {"flows"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete flow given flowId", content =@Content(mediaType = "application/json") )
	})
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
