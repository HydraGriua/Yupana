package com.acme.yupanaapi.service;
import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.repository.FlowRepository;
import com.acme.yupanaapi.domain.service.FlowService;
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

    @Transactional(readOnly = true)
    @Override
    public Flow getFlowById(Long flowId) {
        return flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
    }
    @Override
    public Flow createFlow(Flow flow) {
        return flowRepository.save(flow);
    }

    @Override
    public Flow updateFlow(Long flowId, Flow flowRequest) {
        Flow flow = flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
        flow.setDeadlineDate(flowRequest.getDeadlineDate());
        flow.setCurrentInterestRate(flowRequest.getCurrentInterestRate());
        flow.setCurrentRatePeriod(flowRequest.getCurrentRatePeriod());
        flow.setCurrentRateType(flowRequest.getCurrentRateType());
        flow.setCreditLine(flowRequest.getCreditLine());
        flow.setTotalDebt(flowRequest.getTotalDebt());
        return flowRepository.save(flow);

    }

    @Override
    public ResponseEntity<?> deleteFlow(Long flowId) {
        Flow flow = flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
        flowRepository.delete(flow);
        return ResponseEntity.ok().build();
    }
    @Override
    public List<Flow> getAllByWalletId(Long walletId) {
    	return flowRepository.findAllByWalletId(walletId);
    }
    @Override
    public List<Flow> getAllByDeadlineDate(Date date) {
    	return flowRepository.findAllByDeadlineDate(date);
    }
}