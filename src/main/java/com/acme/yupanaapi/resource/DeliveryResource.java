package com.acme.yupanaapi.resource;

import java.util.Date;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryResource {
	private int id; 
	private String direction;
	private Date deliveryDate;
	private Date deliveryHour;	
	private Float deliveryPrice;
	private String description;

}
