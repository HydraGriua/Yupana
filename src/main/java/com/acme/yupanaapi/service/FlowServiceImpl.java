package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.*;
import com.acme.yupanaapi.domain.repository.*;
import com.acme.yupanaapi.domain.service.FlowService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.acme.yupanaapi.resource.InfoFilter;
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
				.orElseThrow(() -> new ResourceNotFoundException("Flow not found with Id " + flowId));
	}

	@Transactional
	@Override
	public Flow createFlow(Flow flow, int walletId, int sellerId) {
		Wallet wallet = walletRepository.findByIdAndSellerId(walletId, sellerId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"wallet not found with Id " + walletId + " and SellerId " + sellerId));
		flow.setWallet(wallet);
		return flowRepository.save(flow);
	}

	@Transactional
	@Override

	public Flow updateFlow(int flowId, int walletId, int sellerId, Flow flowRequest) {
		if (!walletRepository.existsByIdAndSellerId(walletId, sellerId))
			throw new ResourceNotFoundException("wallet not found with Id " + walletId + " and SellerId " + sellerId);

		return flowRepository.findById(flowId).map(flow -> {
			flow.setDeadlineDate(flowRequest.getDeadlineDate());
			flow.setInterestRate(flowRequest.getInterestRate());
			flow.setRatePeriod(flowRequest.getRatePeriod());
			flow.setRateType(flowRequest.getRateType());
			flow.setCreditLine(flowRequest.getCreditLine());
			flow.setTotalDebt(flowRequest.getTotalDebt());
			return flowRepository.save(flow);
		}).orElseThrow(() -> new ResourceNotFoundException("Flow not found with Id " + flowId));

	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteFlow(int flowId) {
		Flow flow = flowRepository.findById(flowId)
				.orElseThrow(() -> new ResourceNotFoundException("Flow not found with Id " + flowId));
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
		return flowRepository.findAllByWalletIdAndDeadlineDate(walletId, date);
	}

	@Transactional(readOnly = true)
	@Override
	public Flow getLastFlow(int walletId) {
		List<Flow> lista = flowRepository.findAllByWalletId(walletId);
		return lista.get(lista.size() - 1);
	}

	@Override
	public UserWalletResource getData(int walletId) {
		List<Flow> listaF = flowRepository.findAllByWalletId(walletId);
//		for(Flow x : listaF) {
//			System.err.print(x);
//			
//		}
		UserWalletResource userWalletResource = new UserWalletResource();
		if (listaF.size() > 0) {
			Flow flow = listaF.get(listaF.size()-1);
			Wallet wallet = walletRepository.findById(walletId)
					.orElseThrow(() -> new ResourceNotFoundException("Wallet not found with Id "));
			User user = userRepository.findById(wallet.getIdOfUser())
					.orElseThrow(() -> new ResourceNotFoundException("User not found with Id "));
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
			userWalletResource.setCurrencyType(wallet.getCurrencyType());
			userWalletResource.setMaintenancePrice(wallet.getMaintenancePrice());
			userWalletResource.setBalance(wallet.getBalance());
			userWalletResource.setState(wallet.getState());
			userWalletResource.setType(wallet.getType());
			userWalletResource.setDeadlineDate(flow.getDeadlineDate());
			userWalletResource.setLastTransactionDate(flow.getLastTransactionDate());
			userWalletResource.setCurrentInterestRate(flow.getInterestRate());
			userWalletResource.setCurrentRatePeriod(flow.getRatePeriod());
			userWalletResource.setCurrentCapitalization(flow.getCapitalization());
			userWalletResource.setCapitalizationType(flow.getCapitalizationType());
			userWalletResource.setRateType(flow.getRateType());
			userWalletResource.setCreditLine(flow.getCreditLine());
			userWalletResource.setCurrentCreditLine(flow.getCurrentCreditLine());
			userWalletResource.setTotalDebt(flow.getTotalDebt());

		}
		return userWalletResource;
	}

	@Override
	public List<UserWalletResource> getAllUserTransactionById(List<Wallet> wallets) {
		System.out.print("hola");
		List<UserWalletResource> x = new ArrayList<>();
		for (Wallet walletT : wallets) {
			for (Flow flow : flowRepository.findAllByWalletId(walletT.getId())) {
				for (Transaction transac : transactionRepository.findAllByFlowId(flow.getId())) {
					User user = userRepository.findById(walletT.getUser().getId())
							.orElseThrow(() -> new ResourceNotFoundException("User not found with Id "));
					UserWalletResource userWalletResource = new UserWalletResource();
					userWalletResource.setUserId(user.getId());
					userWalletResource.setWalletId(walletT.getId());
					userWalletResource.setFlowId(flow.getId());
					userWalletResource.setDocumentNumber(user.getDocumentNumber());
					userWalletResource.setName(user.getName());
					userWalletResource.setFirstLastname(user.getFirstLastname());
					userWalletResource.setSecondLastname(user.getSecondLastname());
					userWalletResource.setCellphone(user.getCellphone());
					userWalletResource.setDescription(user.getDescription());
					userWalletResource.setCreationDate(walletT.getCreationDate());
					userWalletResource.setMaintenancePrice(walletT.getMaintenancePrice());
					userWalletResource.setBalance(walletT.getBalance());
					userWalletResource.setState(walletT.getState());
					userWalletResource.setType(walletT.getType());
					//userWalletResource
					userWalletResource.setDeadlineDate(flow.getDeadlineDate());
					userWalletResource.setLastTransactionDate(flow.getLastTransactionDate());
					userWalletResource.setCurrentInterestRate(flow.getInterestRate());
					userWalletResource.setCurrentRatePeriod(flow.getRatePeriod());
					userWalletResource.setCurrentCapitalization(flow.getCapitalization());
					userWalletResource.setRateType(flow.getRateType());
					userWalletResource.setCreditLine(flow.getCreditLine());
					userWalletResource.setCurrentCreditLine(flow.getCurrentCreditLine());
					userWalletResource.setTotalDebt(flow.getTotalDebt());
					userWalletResource.setTransaction(transac);
					x.add(userWalletResource);
				}
			}
		}
		return x;
	}
	@Override
	public Flow getActiveFlow(int walletId) {
		System.err.print("\nDIDDDDDDDDDDD: "+ walletId);
		List<Flow> listFlows = flowRepository.findAllByWalletId(walletId);
		Flow flowT = new Flow(); 
		for(Flow x : listFlows) {
			if(x.getState().equals("ACTIVO")) {
				flowT=x;
				break;
			} 
		}
		System.err.print("\n"+ flowT);
		return flowT;
	}
}