package com.kushal.firstapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kushal.firstapp.Model.User;
import com.kushal.firstapp.util.JwtUtil;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private com.kushal.firstapp.Repository.UserRepository userRepository;
    private JwtUtil jwtUtil = new JwtUtil();

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return jwtUtil.generateToken(user.get().getUserId().longValue(), email, user.get().getRole().toString(),
                    user.get().getPhotoUrl());
        } else {
            return null;
        }
    }

}