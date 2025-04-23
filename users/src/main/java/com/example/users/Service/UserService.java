package com.example.users.Service;


import com.example.users.Model.User;
import com.example.users.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id) {
        if(userRepository.findById(id).isPresent()) {
            return userRepository.save(userRepository.findById(id).get());
        }
        return userRepository.findById(id).orElse(null);
    }



}
