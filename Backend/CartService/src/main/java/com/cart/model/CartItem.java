package com.cart.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Document(collection = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    private String id;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    private String productName;

    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String image;
}