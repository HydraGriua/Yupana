package com.acme.yupanaapi.service;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.UserRepository;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
    }

    @Override
    public User getUserByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Document Number " + documentNumber));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
        user.setCellphone(userRequest.getCellphone());
        user.setDescription(userRequest.getDescription());
        user.setSecondLastname(userRequest.getSecondLastname());
        user.setFirstLastname(userRequest.getFirstLastname());
        user.setName(userRequest.getName());
        user.setDocumentType(userRequest.getDocumentType());
        user.setDocumentNumber(userRequest.getDocumentNumber());
        return userRepository.save(user);

    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
