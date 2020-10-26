package com.acme.yupanaapi.domain.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	@Lob
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
	
	@Lob
	@NotNull
	private String description;
	
	public Delivery(@NotNull String direction, @NotNull Date deliveryDate, @NotNull Date deliveryHour,@NotNull Float deliveryPrice, @NotNull String description) {
			this.direction = direction;
			this.deliveryDate = deliveryDate;
			this.deliveryHour = deliveryHour;
			this.deliveryPrice = deliveryPrice;
			this.description = description;
	}
	
	public Delivery() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDeliveryHour() {
		return deliveryHour;
	}

	public void setDeliveryHour(Date deliveryHour) {
		this.deliveryHour = deliveryHour;
	}

	public Float getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Float deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}