package com.acme.yupanaapi.resource;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveDeliveryResource {
	
	@NotNull
	@Size(max = 20)
	private String direction;
	
	@NotNull
	private Date deliveryDate;
	
	@NotNull
	private Date deliveryHour;	
	
	@NotNull
	private Float deliveryPrice;
	
	@NotNull
	@Size(max = 15)
	private String description;
}
