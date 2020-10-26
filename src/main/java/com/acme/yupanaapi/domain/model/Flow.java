package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
	private Long id;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date deadlineDate;

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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	@JsonIgnore
	private Wallet wallet;

}
