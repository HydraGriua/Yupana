package com.acme.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Table(name = "suscripciones")
public class Suscripcion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull
	private Float monto;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date fechaEmision;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date fechaVencimiento;
	
	@Lob
	@NotNull
	private String tipoSuscripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getTipoSuscripcion() {
		return tipoSuscripcion;
	}

	public void setTipoSuscripcion(String tipoSuscripcion) {
		this.tipoSuscripcion = tipoSuscripcion;
	}

	public Suscripcion(@NotNull Float monto, @Null Date fechaEmision, @Null Date fechaVencimiento, @NotNull String tipoSuscripcion) {
		this.monto = monto;
		this.fechaEmision = fechaEmision;
		this.fechaVencimiento = fechaVencimiento;
		this.tipoSuscripcion = tipoSuscripcion;
	}
	
	
}
