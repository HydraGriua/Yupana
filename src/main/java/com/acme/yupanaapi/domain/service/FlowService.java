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

    Flow getFlowById(int flowId);
    Flow createFlow(Flow flow, int walletId, int sellerId);
    Flow updateFlow(int flowId,int walletId, int sellerId, Flow flowRequest);
    ResponseEntity<?> deleteFlow(int flowId);
    List<Flow> getAllByWalletId(int walletId);
    List<Flow> getAllByWalletIdAndDeadlineDate(int walletId, Date date);
    Flow getLastFlow(int walletId);
    UserWalletResource getData(int walletId);
    List<UserWalletResource> getAllUserTransactionById(List<Wallet> wallets);
}

