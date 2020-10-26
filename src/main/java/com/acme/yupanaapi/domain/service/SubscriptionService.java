package com.acme.yupanaapi.domain.service;
import org.springframework.http.ResponseEntity;
import com.acme.yupanaapi.domain.model.Subscription;

import java.util.List;

public interface SubscriptionService {
	Subscription createSubscription(Subscription subscription);
	Subscription updateSubscription(Subscription subscriptionEntity, Long subscriptionId);
	ResponseEntity<?> deleteSubscription(Long subscriptionId);
	Subscription getSubscriptionById(Long subscriptionId);
	List<Subscription> getAllByWalletId(Long walletId);
}
