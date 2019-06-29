package com.store.checkout.service.dtos;

import com.store.checkout.service.repositories.domain.Product;
import lombok.Data;

@Data
public class BasketDto {
    private long basketId;
    private Product product;
    private Integer quantity;
}
