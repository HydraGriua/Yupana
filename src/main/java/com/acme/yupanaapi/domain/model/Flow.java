package com.acme.yupanaapi.domain.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "flows")
public class Flow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private Wallet wallet
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date deadLine;
	@NotNull
	private Float currentInterestRate;
	@NotNull
	private String currentRatePeriod;
	@NotNull
	private String currentRateType;
	@NotNull
	private Float creditLine;
	@NotNull
	private Float totalDebt;
	public Flow(@NotNull Date deadLine, @NotNull Float currentInterestRate, 
			@NotNull String currentRatePeriod, @NotNull String currentRateType, 
			@NotNull Float creditLine, @NotNull Float totalDebt) {
		this.deadLine = deadLine;
		this.currentInterestRate = currentInterestRate;
		this.currentRatePeriod = currentRatePeriod;
		this.currentRateType = currentRateType;
		this.creditLine = creditLine;
		this.totalDebt = totalDebt;
	}
	public Flow() {	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
	public Float getCurrentInterestRate() {
		return currentInterestRate;
	}
	public void setCurrentInterestRate(Float currentInterestRate) {
		this.currentInterestRate = currentInterestRate;
	}
	public String getCurrentRatePeriod() {
		return currentRatePeriod;
	}
	public void setCurrentRatePeriod(String currentRatePeriod) {
		this.currentRatePeriod = currentRatePeriod;
	}
	public String getCurrentRateType() {
		return currentRateType;
	}
	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}
	public Float getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(Float creditLine) {
		this.creditLine = creditLine;
	}
	public Float getTotalDebt() {
		return totalDebt;
	}
	public void setTotalDebt(Float totalDebt) {
		this.totalDebt = totalDebt;
	}
}
