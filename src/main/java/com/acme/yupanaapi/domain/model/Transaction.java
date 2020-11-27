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
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String transactionName;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@NotNull
	private Float interestRate;

	@NotNull
	private int capitalization;
	
	@NotNull
	private String capitalizationType;

	@NotNull
	private String currencyType;

	@NotNull
	private int ratePeriod;

	@NotNull
	private String rateType;

	@NotNull
	private Float netAmount; // total de lo que te cuesta la venta

	@NotNull
	private Float amountPaid; // pagas al contado

	@NotNull
	private String payType;

	@NotNull
	private String description;
	
	@NotNull
	private Float debt; // esta al credito, deuda al credito

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "flow_id", nullable = false)
	@JsonIgnore
	private Flow flow;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "seller_id", nullable = false)
	@JsonIgnore
	private Seller seller;
}
