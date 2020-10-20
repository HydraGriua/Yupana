package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User getUserById(Long userId);
    User getUserByDocumentNumber(String documentNumber);
    User createUser(User user);
    User updateUser(Long userId,User userRequest);
    ResponseEntity<?> deleteUser(Long userId);
}
