package com.acme.yupanaapi.resource;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveSubscriptionResource {
	
	@NotNull
	private Float amount;
	
	@NotNull
	private Date creationDate;
	
	@NotNull
	private Date expirationDate;
	
	@NotNull
	@Size(max = 15)
	private String subscriptionType;
}
