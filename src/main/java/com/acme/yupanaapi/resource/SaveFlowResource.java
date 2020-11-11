package com.acme.yupanaapi.resource;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SaveFlowResource {
	@NotNull
	private Date deadlineDate;
	@NotNull
	private Date lastTransactionDate;
	@NotNull
	private Float currentInterestRate;
	@NotNull
	private int currentRatePeriod;
	@NotNull
	private int currentCapitalization;
	@NotNull
	@Size(max = 30)
	private String currentRateType;
	@NotNull
	private Float creditLine;
	@NotNull
	private Float currentCreditLine;
	@NotNull
	private Float totalDebt;
}
