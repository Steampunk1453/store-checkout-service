package com.store.checkout.service.services;

import com.store.checkout.service.domain.Basket;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Validated
public interface BasketService {

    Basket create(@NotNull(message = "Basket cannot be null") @Valid Basket basket);
    void update(@NotNull(message = "Basket cannot be null") @Valid Basket basket);
    Double getTotalAmount(long basketId);
    void delete(long basketId);

}
