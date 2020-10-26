package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.yupanaapi.domain.model.Suscripcion;




@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{

}
