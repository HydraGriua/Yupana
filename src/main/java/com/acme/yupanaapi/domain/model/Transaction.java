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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "flow_id", nullable = false)
	@JsonIgnore
	private Flow flow;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sale_id", nullable = false)
	@JsonIgnore
	private Sale sale;
}
