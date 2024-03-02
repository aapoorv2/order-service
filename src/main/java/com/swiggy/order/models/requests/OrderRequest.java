package com.swiggy.order.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private Long restaurantId;
    private List<Long> itemIds;
}
