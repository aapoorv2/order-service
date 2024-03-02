package com.swiggy.order.models.dto;

import com.swiggy.order.entities.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private Money price;
}
