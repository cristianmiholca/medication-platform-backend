package com.utcn.medicationplatform.services;

import com.utcn.medicationplatform.entities.User;
import com.utcn.medicationplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID save(User user) {
        return userRepository.save(user).getId();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

}
