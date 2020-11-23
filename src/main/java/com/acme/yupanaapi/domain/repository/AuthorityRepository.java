package com.acme.yupanaapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme.yupanaapi.domain.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

}
