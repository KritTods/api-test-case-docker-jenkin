
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserFullNameById(int id) {
        User user = userRepository.findById(id);
        return user.getFirstName() + " " + user.getLastName();
    }
}
