package com.acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.entities.Suscripcion;



@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{

}
