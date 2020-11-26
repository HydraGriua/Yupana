package com.acme.yupanaapi.service;
import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.repository.UserRepository;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Document Number " + documentNumber));
    }

    @Transactional
    @Override
    public User createUser(User user) {
    	user.setId(0);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(int userId, User userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setCellphone(userRequest.getCellphone());
            user.setDescription(userRequest.getDescription());
            user.setSecondLastname(userRequest.getSecondLastname());
            user.setFirstLastname(userRequest.getFirstLastname());
            user.setName(userRequest.getName());
            user.setDocumentType(userRequest.getDocumentType());
            user.setDocumentNumber(userRequest.getDocumentNumber());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
    }
    
    @Transactional
    @Override
    public ResponseEntity<?> deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with Id " + userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
