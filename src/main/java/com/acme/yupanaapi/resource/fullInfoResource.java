package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.Flow;
import com.acme.yupanaapi.domain.model.Seller;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class fullInfoResource {
	private Transaction transaction;
	private Flow flow;
	//private Sale sale;
	private Seller seller;
	private Wallet wallet;
	private Boolean deliveryBool; //true = SI, false = NO
	private Boolean subscriptionBool; //true = SI, false = NO
}