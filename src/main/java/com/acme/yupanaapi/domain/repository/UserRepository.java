package com.acme.yupanaapi.domain.repository;

import com.acme.yupanaapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
