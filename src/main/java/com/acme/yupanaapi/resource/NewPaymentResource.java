package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPaymentResource {
	private Transaction transaction;
	private int walletId;
}
