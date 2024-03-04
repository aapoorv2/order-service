package com.swiggy.order.controllers;

import com.swiggy.order.exceptions.UserAlreadyExistsException;
import com.swiggy.order.models.requests.UserRequest;
import com.swiggy.order.repositories.UserRepository;
import com.swiggy.order.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        try {
            String response = userService.register(userRequest.getUsername(), userRequest.getPassword(), userRequest.getCity());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}