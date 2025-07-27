package com.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.exception.ProductNotFountException;
import com.product.model.Product;
import com.product.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
//    	product.setId(productRepository.count() + 1 + "");
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Product existingProduct = productRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ProductNotFountException("Product not found with ID: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    @Override
    public String deleteProduct(String id) {
        if (!productRepository.existsById(Integer.parseInt(id))) {
            throw new ProductNotFountException("Product not found with ID: " + id);
        }
        productRepository.deleteById(Integer.parseInt(id));
        return "Product deleted successfully";
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ProductNotFountException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

	@Override
	public List<Product> getProductsByStatus(String status) {
		return productRepository.findByStatus(status);
	}
}
