package com.store.checkout.service.services;

import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderRequest;
import com.store.checkout.service.domain.Basket;

public interface OrderService {
    Basket saveBasket(OrderRequest orderRequest);
    Basket saveProduct(BasketDto basketDto);
}
