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
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	@NotNull
	private String direction;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	
	@NotNull
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date deliveryHour;
	
	@NotNull
	private Float deliveryPrice;

	@NotNull
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "transaction_id", nullable = false)
	@JsonIgnore
	private Transaction transaction;	
}