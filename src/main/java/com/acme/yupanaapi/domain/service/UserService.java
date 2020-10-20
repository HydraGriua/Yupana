package com.acme.yupanaapi.domain.service;

import com.acme.yupanaapi.domain.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUserById(Long userId);
    User createUser(User user);
    User udpateUser(Long userId,User userRequest);
    ResponseEntity<?> deleteUser(Long userId);
}
