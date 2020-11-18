package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
	private Long id;
	
	@Lob
	@NotNull
	private String type;
	
	@Lob
	@NotNull
	private String currencyType;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date creationDate;
	
	@Lob
	@NotNull
	private String state;
	
	@NotNull
	private Float balance;
	
	@NotNull
	private Float maintenancePrice;

	@NotNull
	private Long idOfUser;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "seller_id", nullable = false)
	@JsonIgnore
	private Seller seller;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
