package com.store.checkout.service.services;

import com.store.checkout.service.domain.BasketProduct;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface BasketProductService {
    BasketProduct create(@NotNull(message = "The products for basket cannot be null") @Valid BasketProduct basketProduct);
}
