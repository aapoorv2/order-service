package com.swiggy.order.models.dto;

import com.swiggy.order.entities.Money;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ItemDTO {
    private Long id;
    private String name;
    private Money price;
}
