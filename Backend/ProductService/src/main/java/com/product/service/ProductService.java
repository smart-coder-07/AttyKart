package com.product.service;
import java.util.List;

import com.product.model.Product;

public interface ProductService {

	
	    Product addProduct(Product product);
	    Product updateProduct(String id, Product product);
	    String deleteProduct(String id);
	    Product getProductById(String id);
	    List<Product> getAllProducts();
	    List<Product> getProductsByCategory(String category);
	    List<Product> getProductsByStatus(String status);
	
}
