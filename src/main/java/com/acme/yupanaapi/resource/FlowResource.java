package com.acme.yupanaapi.resource;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowResource {
	private Integer id;
	private Date deadlineDate;
	private Date lastTransactionDate;
	private Float currentInterestRate;
	private int currentRatePeriod;
	private int currentCapitalization;
	private String currentRateType;
	private Float creditLine;
	private Float currentCreditLine;
	private Float totalDebt;
}
