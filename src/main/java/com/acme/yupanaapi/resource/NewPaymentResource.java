package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.Transaction;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewPaymentResource {
	
	private Transaction transaction;
	private String amountToPay;
	private String description;
	private String creationDate;
	private int idSeller;
	private int flowId;
	private int idWallet;
	private String transactionType;
}
