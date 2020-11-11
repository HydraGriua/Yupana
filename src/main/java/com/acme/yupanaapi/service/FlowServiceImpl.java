package com.acme.yupanaapi.service;
import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.repository.FlowRepository;
import com.acme.yupanaapi.domain.repository.WalletRepository;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.domain.service.TransactionService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Transactional(readOnly = true)
    @Override
    public Flow getFlowById(Long flowId) {
        return flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
    }
    @Transactional
    @Override
    public Flow createFlow(Flow flow, Long walletId, Long sellerId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId,sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("wallet not found with Id " + walletId +
                        " and SellerId " + sellerId));
        flow.setWallet(wallet);
        return flowRepository.save(flow);
    }

    @Transactional
    @Override
    public Flow updateFlow(Long flowId, Long walletId, Long sellerId, Flow flowRequest) {
        if(!walletRepository.existsByIdAndSellerId(walletId,sellerId))
            throw new ResourceNotFoundException("wallet not found with Id " + walletId +
                    " and SellerId " + sellerId);

        return flowRepository.findById(flowId).map(flow -> {
            flow.setDeadlineDate(flowRequest.getDeadlineDate());
            flow.setCurrentInterestRate(flowRequest.getCurrentInterestRate());
            flow.setCurrentRatePeriod(flowRequest.getCurrentRatePeriod());
            flow.setCurrentRateType(flowRequest.getCurrentRateType());
            flow.setCreditLine(flowRequest.getCreditLine());
            flow.setTotalDebt(flowRequest.getTotalDebt());
            return flowRepository.save(flow);
        }).orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));

    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteFlow(Long flowId) {
        Flow flow = flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
        flowRepository.delete(flow);
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Flow> getAllByWalletId(Long walletId) {
    	return flowRepository.findAllByWalletId(walletId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Flow> getAllByDeadlineDate(Date date) {
    	return flowRepository.findAllByDeadlineDate(date);
    }
    
}