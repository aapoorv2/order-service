package com.swiggy.order.entities;

import com.swiggy.order.enums.Status;
import com.swiggy.order.models.dto.ItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<ItemDTO> items;

    private Money totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(User user, List<ItemDTO> items) {
        this.user = user;
        this.items = items;
        this.status = Status.UNASSIGNED;
        this.totalPrice = calculateTotalPrice(items);
    }

    private Money calculateTotalPrice(List<ItemDTO> items) {
        Money total = new Money();
        for (ItemDTO item : items) {
            total = total.add(item.getPrice());
        }
        return total;
    }
}
