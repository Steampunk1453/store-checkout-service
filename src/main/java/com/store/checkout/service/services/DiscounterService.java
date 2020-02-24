package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;
import com.store.checkout.service.services.dtos.DiscountDto;

public interface DiscounterService {
    DiscountDto getDiscount(Product product, Integer quantity);
}
