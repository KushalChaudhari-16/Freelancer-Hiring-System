package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kushal.firstapp.Model.User;
import com.kushal.firstapp.Model.User.Role;
import com.kushal.firstapp.Repository.UserRepository;
import com.kushal.firstapp.service.CloudinaryService;
import com.kushal.firstapp.service.UserService;

import java.util.Collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @RestController   only for jsp comment remove for backend

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired 
    private UserRepository userRepository;



    // @PostMapping("/register")
    // public User registerUser(@RequestBody User user) {
    //     return userService.registerUser(user);
    // }



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {

        try {
            Role userRole;
            try {
                userRole = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role provided.");
            }
            // Upload profile photo if provided
            String photoUrl = null;
            if (photo != null && !photo.isEmpty()) {
                photoUrl = cloudinaryService.uploadFileforProfilePhoto(photo);
            }

            // Create user
            User user=new User();
           
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(userRole);
            user.setPhotoUrl(photoUrl);

            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading profile photo.");
        }
    }

    @GetMapping("/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        String token = userService.loginUser(user.getEmail(), user.getPassword());

        if (token != null) {
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
