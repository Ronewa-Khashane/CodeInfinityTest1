package com.khashane.codeinfinitytest1.service;

import com.khashane.codeinfinitytest1.domain.User;
import com.khashane.codeinfinitytest1.exception.DuplicateIdException;
import com.khashane.codeinfinitytest1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new DuplicateIdException("A user with this ID Number already exists.");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> updateUser(String id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setSurname(userDetails.getSurname());
            existingUser.setDateOfBirth(userDetails.getDateOfBirth());
            return userRepository.save(existingUser);
        });
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}