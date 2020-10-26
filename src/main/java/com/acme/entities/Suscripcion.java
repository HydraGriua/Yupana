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
@Table(name = "suscripciones")
public class Suscripcion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name = "monto", nullable = false)
	private Float monto;
	
	@Column(name = "fecha_emision", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date fechaEmision;
	
	@Column(name = "fecha_vencimiento", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date fechaVencimiento;
	
	@Column(name = "tipo_suscripcion", length = 12, nullable = false)
	private String tipoSuscripcion;
}
