package com.store.checkout.service.services;

import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderDto;
import com.store.checkout.service.domain.Basket;

public interface OrderService {
    Basket saveBasket(OrderDto orderDto);
    Basket saveProduct(BasketDto basketDto);
}
