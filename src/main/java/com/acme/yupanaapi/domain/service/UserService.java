package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User getUserById(Integer userId);
    User getUserByDocumentNumber(String documentNumber);
    User createUser(User user);
    User updateUser(Integer userId,User userRequest);
    ResponseEntity<?> deleteUser(Integer userId);
}
