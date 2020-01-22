package com.store.checkout.service.services.dtos;

import com.store.checkout.service.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private long basketId;
    private Product product;
    private Integer quantity;
}
