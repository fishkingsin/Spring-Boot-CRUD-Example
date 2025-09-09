package com.example.springbootpostgresqlcrud.repository;

import com.example.springbootpostgresqlcrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
