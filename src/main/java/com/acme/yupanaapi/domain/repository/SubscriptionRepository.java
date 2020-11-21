package com.acme.yupanaapi.domain.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.yupanaapi.domain.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{
    List<Subscription> findAllByWalletId(int walletId);

}
