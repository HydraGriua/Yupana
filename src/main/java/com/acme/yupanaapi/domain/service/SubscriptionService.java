package com.acme.yupanaapi.domain.service;
import org.springframework.http.ResponseEntity;
import com.acme.yupanaapi.domain.model.Subscription;

import java.util.List;

public interface SubscriptionService {
	Subscription createSubscription(Subscription subscription, Integer walletId);
	Subscription updateSubscription(Subscription subscriptionEntity, Integer subscriptionId, Integer walletId);
	ResponseEntity<?> deleteSubscription(Integer subscriptionId);
	Subscription getSubscriptionById(Integer subscriptionId);
	List<Subscription> getAllByWalletId(Integer walletId);
}
