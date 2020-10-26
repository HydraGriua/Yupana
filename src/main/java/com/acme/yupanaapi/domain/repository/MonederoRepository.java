package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.yupanaapi.domain.model.Monedero;





@Repository
public interface MonederoRepository extends JpaRepository<Monedero, Long>{

}
