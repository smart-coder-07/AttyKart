package com.product.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be greater than zero")
    private Double price;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotBlank(message = "Image URL cannot be empty")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid image URL format")
    private String image;

    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(ACTIVE|DISCONTINUED)$", message = "Product status must be either 'ACTIVE' or 'DISCONTINUED'")
    private String status;
}
