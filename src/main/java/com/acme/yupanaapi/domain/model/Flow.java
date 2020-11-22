package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "flows")
public class Flow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date deadlineDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date lastTransactionDate;

	@NotNull
	private Float currentInterestRate;

	@NotNull
	private int currentRatePeriod;

	@NotNull
	private int currentCapitalization;

	@NotNull
	private String currentRateType;

	@NotNull
	private Float creditLine;
	
	@NotNull
	private Float currentCreditLine; 
	
	@NotNull
	private Float totalDebt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	@JsonIgnore
	private Wallet wallet;

}
