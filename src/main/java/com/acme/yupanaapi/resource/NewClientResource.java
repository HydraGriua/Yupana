package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Wallet;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NewClientResource {
	private Wallet wallet;
	private Flow flow;
	private Subscription subscription;
	private Delivery delivery;
	private Boolean subscriptionBool;
}
