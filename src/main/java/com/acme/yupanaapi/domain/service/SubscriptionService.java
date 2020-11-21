package com.acme.yupanaapi.domain.service;
import org.springframework.http.ResponseEntity;
import com.acme.yupanaapi.domain.model.Subscription;

import java.util.List;

public interface SubscriptionService {
	Subscription createSubscription(Subscription subscription, int walletId);
	Subscription updateSubscription(Subscription subscriptionEntity, int subscriptionId, int walletId);
	ResponseEntity<?> deleteSubscription(int subscriptionId);
	Subscription getSubscriptionById(int subscriptionId);
	List<Subscription> getAllByWalletId(int walletId);
}
