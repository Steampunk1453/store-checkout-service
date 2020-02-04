package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;

import java.math.BigDecimal;

public interface DiscounterService {
    BigDecimal getTotalPrice(Product product, Integer quantity);
}
