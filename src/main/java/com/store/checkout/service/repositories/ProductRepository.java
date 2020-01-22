package com.store.checkout.service.repositories;

import com.store.checkout.service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
