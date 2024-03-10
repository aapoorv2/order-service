package com.swiggy.order.services;

import com.swiggy.order.entities.User;
import com.swiggy.order.enums.City;
import com.swiggy.order.exceptions.UserAlreadyExistsException;
import com.swiggy.order.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.swiggy.order.constants.Error.USER_ALREADY_EXISTS;
import static com.swiggy.order.constants.Success.USER_CREATED;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    public String register(String username, String password, City city) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }
        User user = User.builder()
                    .username(username)
                    .password(encoder.encode(password))
                    .city(city)
                    .build();

        userRepository.save(user);
        return USER_CREATED + user.getId();
    }
}
