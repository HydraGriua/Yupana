package com.acme.yupanaapi.service;
import com.acme.yupanaapi.domain.model.*;
import com.acme.yupanaapi.domain.repository.*;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

import com.acme.yupanaapi.resource.UserWalletResource;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    @Override
    public Flow getFlowById(int flowId) {
        return flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
    }
    @Transactional
    @Override
    public Flow createFlow(Flow flow, int walletId, int sellerId) {
        Wallet wallet = walletRepository.findByIdAndSellerId(walletId,sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("wallet not found with Id " + walletId +
                        " and SellerId " + sellerId));
        flow.setWallet(wallet);
        return flowRepository.save(flow);
    }

    @Transactional
    @Override
    public Flow updateFlow(int flowId, int walletId, int sellerId, Flow flowRequest) {
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
    public ResponseEntity<?> deleteFlow(int flowId) {
        Flow flow = flowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flow not found with Id " + flowId));
        flowRepository.delete(flow);
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Flow> getAllByWalletId(int walletId) {
    	return flowRepository.findAllByWalletId(walletId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Flow> getAllByWalletIdAndDeadlineDate(int walletId, Date date) {
    	return flowRepository.findAllByWalletIdAndDeadlineDate(walletId,date);
    }

    @Transactional(readOnly = true)
    @Override
    public Flow getLastFlow(int walletId){
        List<Flow> lista = flowRepository.findAllByWalletId(walletId);
        return lista.get(lista.size()-1);
    }

    @Override
    public UserWalletResource getData(int walletId) {
        List<Flow> listaF = flowRepository.findAllByWalletId(walletId);
        Flow flow = listaF.get(listaF.size()-1);

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Wallet not found with Id "));
        User user = userRepository.findById(wallet.getIdOfUser()).orElseThrow(() -> new ResourceNotFoundException(
                "User not found with Id "));

        UserWalletResource userWalletResource = new UserWalletResource();
        userWalletResource.setUserId(user.getId());
        userWalletResource.setWalletId(wallet.getId());
        userWalletResource.setFlowId(flow.getId());
        userWalletResource.setDocumentNumber(user.getDocumentNumber());
        userWalletResource.setName(user.getName());
        userWalletResource.setFirstLastname(user.getFirstLastname());
        userWalletResource.setSecondLastname(user.getSecondLastname());
        userWalletResource.setCellphone(user.getCellphone());
        userWalletResource.setDescription(user.getDescription());
        userWalletResource.setCreationDate(wallet.getCreationDate());
        userWalletResource.setMaintenancePrice(wallet.getMaintenancePrice());
        userWalletResource.setBalance(wallet.getBalance());
        userWalletResource.setState(wallet.getState());
        userWalletResource.setType(wallet.getType());
        userWalletResource.setDeadlineDate(flow.getDeadlineDate());
        userWalletResource.setLastTransactionDate(flow.getLastTransactionDate());
        userWalletResource.setCurrentInterestRate(flow.getCurrentInterestRate());
        userWalletResource.setCurrentRatePeriod(flow.getCurrentRatePeriod());
        userWalletResource.setCurrentCapitalization(flow.getCurrentCapitalization());
        userWalletResource.setCurrentRateType(flow.getCurrentRateType());
        userWalletResource.setCreditLine(flow.getCreditLine());
        userWalletResource.setCurrentCreditLine(flow.getCurrentCreditLine());
        userWalletResource.setTotalDebt(flow.getTotalDebt());
        return userWalletResource;
    }
}