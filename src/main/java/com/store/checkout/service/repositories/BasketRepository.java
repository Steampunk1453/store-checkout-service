package com.store.checkout.service.repositories;

import com.store.checkout.service.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
