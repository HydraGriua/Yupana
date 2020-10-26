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
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull
	private Float amount;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date creationDate;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date expirationDate;
	
	@Lob
	@NotNull
	private String subscriptionType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	@JsonIgnore
	private Wallet wallet;
}
