package com.swiggy.order.models.requests;

import com.swiggy.order.enums.City;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private City city;
}
