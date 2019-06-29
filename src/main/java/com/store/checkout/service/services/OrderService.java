package com.store.checkout.service.services;

import com.store.checkout.service.dtos.BasketDto;
import com.store.checkout.service.dtos.OrderRequest;
import com.store.checkout.service.repositories.domain.Basket;

public interface OrderService {
    Basket saveBasket(OrderRequest orderRequest);
    Basket saveProduct(BasketDto basketDto);
}
