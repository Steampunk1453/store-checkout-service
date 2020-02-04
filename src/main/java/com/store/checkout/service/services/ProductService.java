package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ProductService {
    Product get(@Min(value = 1L, message = "Invalid product ID") long id);
    Product save(@NotNull(message = "The product cannot be null") @Valid Product product);
}
