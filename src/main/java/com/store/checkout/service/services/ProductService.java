package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;

import javax.validation.constraints.Min;

public interface ProductService {
    Product getProduct(@Min(value = 1L, message = "Invalid product ID") long id);
}
