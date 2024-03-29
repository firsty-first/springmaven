package com.springmaven.springmaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@RestController



public class ProductController {
    @GetMapping("/test")
  public  static String  test()
    {
        return "Hi i am running";
    }
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @PostMapping("/check-expiry")
    public boolean checkExpiry(@RequestBody Map<String, String> requestBody) {
        // Extract productId from the JSON body
        String productId = requestBody.get("productId");

        // Validate productId
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }

        // Retrieve product from the database using productId
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = optionalProduct.get();
        LocalDateTime now = LocalDateTime.now();

        // Convert expiryTime string to LocalDateTime
        LocalDateTime expiryTime = LocalDateTime.parse(product.getExpiryTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Check if current time is after expiry time or not
        return now.isAfter(expiryTime);
    }

}

