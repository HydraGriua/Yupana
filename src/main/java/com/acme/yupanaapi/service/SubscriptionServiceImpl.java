package com.acme.yupanaapi.service;


import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.repository.SubscriptionRepository;
import com.acme.yupanaapi.domain.service.SubscriptionService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;

import java.util.List;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {


	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Transactional
	@Override
	public Subscription createSubscription(Subscription subscription, Integer walletId) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new ResourceNotFoundException("wallet not found with Id " + walletId));
		subscription.setWallet(wallet);
		return subscriptionRepository.save(subscription);
	}

	@Transactional
	@Override
	public Subscription updateSubscription(Subscription subscriptionEntity, Integer subscriptionId, Integer walletId) {
		if(!walletRepository.existsById(walletId))
			throw new ResourceNotFoundException("wallet not found with Id " + walletId);

		return subscriptionRepository.findById(subscriptionId).map(subscription -> {
			subscription.setCreationDate(subscriptionEntity.getCreationDate());
			subscription.setExpirationDate(subscriptionEntity.getExpirationDate());
			subscription.setAmount(subscriptionEntity.getAmount());
			subscription.setSubscriptionType(subscriptionEntity.getSubscriptionType());
			return subscriptionRepository.save(subscription);
		}).orElseThrow(()-> new ResourceNotFoundException(
						"Subscription not found with Id"+ subscriptionId));
	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteSubscription(Integer subscriptionId) {
		Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + subscriptionId));
		subscriptionRepository.delete(subscription);
        return ResponseEntity.ok().build();
		
	}

	@Transactional(readOnly =true)
	@Override
	public Subscription getSubscriptionById(Integer subscriptionId) {
		return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + subscriptionId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Subscription> getAllByWalletId(Integer walletId) {
		return subscriptionRepository.findAllByWalletId(walletId);
	}


}
