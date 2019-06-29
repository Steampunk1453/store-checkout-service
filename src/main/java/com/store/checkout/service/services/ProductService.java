package com.store.checkout.service.services;

import com.store.checkout.service.repositories.domain.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ProductService {

    @NotNull Iterable<Product> getAllProducts();

    Product getProduct(@Min(value = 1L, message = "Invalid product ID") long id);

    Product save(Product product);

}
