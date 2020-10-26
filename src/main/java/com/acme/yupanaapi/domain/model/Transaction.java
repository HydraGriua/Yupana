package com.acme.yupanaapi.domain.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Temporal(TemporalType.DATE)
	private String transactionName;
	@NotNull
	private Date transactionDate;
	@NotNull
	private Float amount;
	@NotNull
	private Float interestRate;
	@NotNull
	private String capitalization;
	@NotNull
	private String currencyType;
	@NotNull
	private String ratePeriod;
	@NotNull
	private String rateType;
	public Transaction(@NotNull String transactionName, @NotNull Date transactionDate, @NotNull Float amount,
			@NotNull Float interestRate, @NotNull String capitalization, @NotNull String currencyType,
			@NotNull String ratePeriod, @NotNull String rateType) {
		this.transactionName = transactionName;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.interestRate = interestRate;
		this.capitalization = capitalization;
		this.currencyType = currencyType;
		this.ratePeriod = ratePeriod;
		this.rateType = rateType;
	}
	public Transaction() {}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Float getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}
	public String getCapitalization() {
		return capitalization;
	}
	public void setCapitalization(String capitalization) {
		this.capitalization = capitalization;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getRatePeriod() {
		return ratePeriod;
	}
	public void setRatePeriod(String ratePeriod) {
		this.ratePeriod = ratePeriod;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
}
