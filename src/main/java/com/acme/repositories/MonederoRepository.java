package com.acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.entities.Monedero;



@Repository
public interface MonederoRepository extends JpaRepository<Monedero, Long>{

}
