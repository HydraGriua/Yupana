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
@Table(name = "wallets")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String type;
	
	@NotNull
	private String currencyType;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@NotNull
	private String state;
	
	@NotNull
	private Float balance;
	
	@NotNull
	private Float maintenancePrice;

	@NotNull
	private Float maintenancePeriod;
	@NotNull
	private String maintenancePeriodType;
	
	@NotNull
	private int idOfUser;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "seller_id", nullable = false)
	@JsonIgnore
	private Seller seller;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
}
