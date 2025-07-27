package com.order.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItem {
    
    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    @NotBlank(message = "Product name cannot be empty")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    private String image;
}