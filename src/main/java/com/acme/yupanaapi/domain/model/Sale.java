package com.acme.yupanaapi.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
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

	public Sale(@NotNull Date saleDate, @Null Date saleTime, @NotNull Float netAmount, @NotNull float salePaid, @NotNull String paymentPay, @NotNull String description) {
		this.saleDate = saleDate;
		this.saleTime = saleTime;
		this.netAmount = netAmount;
		this.salePaid = salePaid;
		this.paymentPay = paymentPay;
		this.description = description;
	}
	public Sale() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Float netAmount) {
		this.netAmount = netAmount;
	}

	public Float getSalePaid() {
		return salePaid;
	}

	public void setSalePaid(Float salePaid) {
		this.salePaid = salePaid;
	}

	public String getPaymentPay() {
		return paymentPay;
	}

	public void setPaymentPay(String paymentPay) {
		this.paymentPay = paymentPay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
