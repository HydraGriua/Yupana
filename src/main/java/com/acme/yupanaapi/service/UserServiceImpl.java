package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.UserRepository;
import com.acme.yupanaapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User udpateUser(Long userId, User userRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        return null;
    }
}
