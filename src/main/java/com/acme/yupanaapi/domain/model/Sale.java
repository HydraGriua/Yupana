package com.acme.yupanaapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date saleDate;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date saleTime;
	
	@NotNull
	private Float netAmount;
	
	@NotNull
	private Float salePaid;
	
	@Lob
	@NotNull
	private String paymentPay;
	
	@Lob
	@NotNull
	private String description;

	
}
