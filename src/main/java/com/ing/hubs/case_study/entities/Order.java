package com.ing.hubs.case_study.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "orders") // Rename the table to "orders"
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;
    private int size;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Date createDate;

    public enum OrderSide {
        BUY, SELL
    }

    public enum OrderStatus {
        PENDING, MATCHED, CANCELED
    }
}
