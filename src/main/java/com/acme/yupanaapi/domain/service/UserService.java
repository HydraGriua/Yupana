package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User getUserById(int userId);
    User getUserByDocumentNumber(String documentNumber);
    User createUser(User user);
    User updateUser(int userId,User userRequest);
    ResponseEntity<?> deleteUser(int userId);
}
