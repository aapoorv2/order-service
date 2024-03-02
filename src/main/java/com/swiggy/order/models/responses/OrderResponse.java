package com.swiggy.order.models.responses;

import com.swiggy.order.entities.Money;
import com.swiggy.order.enums.Status;
import com.swiggy.order.models.dto.ItemDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long id;
    private List<ItemDTO> items;
    private Money totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
}
