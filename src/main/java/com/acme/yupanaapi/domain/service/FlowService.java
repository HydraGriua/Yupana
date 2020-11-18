package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Flow;

import java.util.Date;
import java.util.List;

import com.acme.yupanaapi.resource.UserWalletResource;
import org.springframework.http.ResponseEntity;

public interface FlowService {
    Flow getFlowById(Long flowId);
    Flow createFlow(Flow flow, Long walletId, Long sellerId);
    Flow updateFlow(Long flowId,Long walletId, Long sellerId, Flow flowRequest);
    ResponseEntity<?> deleteFlow(Long flowId);
    List<Flow> getAllByWalletId(Long walletId);
    List<Flow> getAllByWalletIdAndDeadlineDate(Long walletId, Date date);
    Flow getLastFlow(Long walletId);
    UserWalletResource getData(Long walletId);
}