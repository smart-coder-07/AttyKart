package com.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String id;
    private String userId;
    private String productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String image;
}