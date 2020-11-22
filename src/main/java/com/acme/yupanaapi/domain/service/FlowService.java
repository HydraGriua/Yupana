package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Flow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.resource.UserWalletResource;
import org.springframework.http.ResponseEntity;

public interface FlowService {
    Flow getFlowById(Integer flowId);
    Flow createFlow(Flow flow, Integer walletId, Integer sellerId);
    Flow updateFlow(Integer flowId,Integer walletId, Integer sellerId, Flow flowRequest);
    ResponseEntity<?> deleteFlow(Integer flowId);
    List<Flow> getAllByWalletId(Integer walletId);
    List<Flow> getAllByWalletIdAndDeadlineDate(Integer walletId, Date date);
    Flow getLastFlow(Integer walletId);
    UserWalletResource getData(Integer walletId);
}
