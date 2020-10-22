package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.Flow;
import org.springframework.http.ResponseEntity;


public interface FlowService {
    Flow getFlowById(Long flowId);
    Flow createFlow(Flow flow);
    Flow updateFlow(Long flowId,Flow flowRequest);
    ResponseEntity<?> deleteFlow(Long flowId);
}