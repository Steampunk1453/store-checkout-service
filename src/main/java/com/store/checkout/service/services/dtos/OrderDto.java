package com.store.checkout.service.services.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<BasketDto> baskets;
}
