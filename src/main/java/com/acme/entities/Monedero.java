package com.acme.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "monederos")
public class Monedero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tipo", length = 12, nullable = false)
	private String tipo;
	
	@Column(name = "tipo_moneda", length = 12, nullable = false)
	private String tipoMoneda;
	
	@Column(name = "fecha_emision", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date fechaEmision;
	
	@Column(name = "estado", length = 10, nullable = false)
	private String estado;
	
	@Column(name = "saldo", nullable = false)
	private Float saldo;
	
	@Column(name = "costo_mantenimiento", nullable = false)
	private Float costoMantenimiento;
}
