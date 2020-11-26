package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


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
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date deadlineDate;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date lastTransactionDate;

	@NotNull
	private Float interestRate;

	@NotNull
	private int ratePeriod; //Periodo de pago
	@NotNull
	private String ratePeriodType;
	
	@NotNull
	private int capitalization;
	@NotNull
	private String capitalizationType;
	
	@NotNull
	private String rateType; //Tipo de tasa

	@NotNull
	private Float creditLine;
	
	@NotNull
	private Float currentCreditLine; 
	
	@NotNull
	private Float totalDebt;
	
	@NotNull
	private String state;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	@JsonIgnore
	private Wallet wallet;
}
