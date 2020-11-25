package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.Delivery;
import com.acme.yupanaapi.domain.model.Transaction;
import com.acme.yupanaapi.domain.model.Wallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewSellResource {
	private int idWallet;
	private Transaction transaction;
	private Delivery delivery;
	private Wallet wallet;
}
