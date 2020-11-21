package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	@NotNull
	private Float amount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	

	@NotNull
	private String subscriptionType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	@JsonIgnore
	private Wallet wallet;
}
