package com.springmaven.springmaven;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
//alternativelly/popularly known as DAO