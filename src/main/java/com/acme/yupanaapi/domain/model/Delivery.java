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
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	@NotNull
	private String direction;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date deliveryDate;
	
	@Temporal(TemporalType.DATE)
	@NotNull
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