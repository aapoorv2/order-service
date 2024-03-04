package com.swiggy.order.services;

import com.swiggy.order.entities.User;
import com.swiggy.order.enums.City;
import com.swiggy.order.exceptions.UserAlreadyExistsException;
import com.swiggy.order.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testCreatingAUserSuccess() {
        userService.register("user", "password", City.DELHI);
        verify(userRepository, times(1)).save(any());
    }
    @Test
    void testThrowingUserAlreadyExistsExceptionWhenUsingTheSameUsername() {
        String username = "user";
        String password = "password";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(username, password, City.MUMBAI);
        });
        verify(userRepository, never()).save(any());
    }

}