package com.acme.yupanaapi.domain.model;

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
@Table(name = "monederos")
public class Monedero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@NotNull
	private String tipo;
	
	@Lob
	@NotNull
	private String tipoMoneda;
	
	@NotNull
	@Temporal(TemporalType.TIME)
	private Date fechaEmision;
	
	@Lob
	@NotNull
	private String estado;
	
	@NotNull
	private Float saldo;
	
	@NotNull
	private Float costoMantenimiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	public Float getCostoMantenimiento() {
		return costoMantenimiento;
	}

	public void setCostoMantenimiento(Float costoMantenimiento) {
		this.costoMantenimiento = costoMantenimiento;
	}

	public Monedero(@NotNull String tipo, @NotNull String tipoMoneda, @Null Date fechaEmision, @NotNull String estado, @NotNull Float saldo, @NotNull Float costoMantenimiento) {
		this.tipo = tipo;
		this.tipoMoneda = tipoMoneda;
		this.fechaEmision = fechaEmision;
		this.estado = estado;
		this.saldo = saldo;
		this.costoMantenimiento = costoMantenimiento;
	}

	
	
	
}
