package com.store.checkout.service.services;

import com.store.checkout.service.domain.BasketProduct;

import java.math.BigDecimal;
import java.util.List;

public interface PriceCalculatorService {
    BigDecimal calculateTotalPrice(List<BasketProduct> basketProducts);
}
