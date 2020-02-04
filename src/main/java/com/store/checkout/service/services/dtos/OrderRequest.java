package com.store.checkout.service.services.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<BasketDto> baskets;
}
