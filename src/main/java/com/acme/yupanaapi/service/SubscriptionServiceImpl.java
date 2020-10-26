package com.acme.yupanaapi.service;


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

	@Override
	public Subscription createSubscription(Subscription subscription) {
		return subscriptionRepository.save(subscription);
		
	}

	@Override
	public Subscription updateSubscription(Subscription subscriptionEntity, Long subscriptionId) {
		Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(()-> new ResourceNotFoundException("Subscription not found with Id"+ subscriptionId));
		subscription.setCreationDate(subscriptionEntity.getCreationDate());
		subscription.setExpirationDate(subscriptionEntity.getExpirationDate());
		subscription.setAmount(subscriptionEntity.getAmount());
		subscription.setSubscriptionType(subscriptionEntity.getSubscriptionType());
		return subscriptionRepository.save(subscription);
	}

	@Override
	public ResponseEntity<?> deleteSubscription(Long subscriptionId) {
		Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + subscriptionId));
		subscriptionRepository.delete(subscription);
        return ResponseEntity.ok().build();
		
	}

	@Transactional(readOnly =true)
	@Override
	public Subscription getSubscriptionById(Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with Id " + subscriptionId));
	}

	@Override
	public List<Subscription> getAllByWalletId(Long walletId) {
		return subscriptionRepository.findAllByWalletId(walletId);
	}


}
