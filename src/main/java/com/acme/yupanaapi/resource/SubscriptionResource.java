package com.acme.yupanaapi.resource;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResource {
	private Long id;	
	private Float amount;
	private Date creationDate;
	private Date expirationDate;
	private String subscriptionType;
}
