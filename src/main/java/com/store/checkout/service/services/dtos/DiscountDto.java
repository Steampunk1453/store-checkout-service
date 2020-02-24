package com.store.checkout.service.services.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountDto {
    private BigDecimal productTotalPrice;
    private boolean isDiscounted;
}
