package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinancialDiscounterService implements DiscounterService {

    public static final String PROMOTION_DISCOUNT_PRODUCT_CODE = "TSHIRT";
    public static final int PROMOTION_PRODUCT_DISCOUNT = 1;
    public static final int PROMOTION_PRODUCT_DISCOUNT_QUANTITY = 3;

    @Override
    public BigDecimal getTotalPrice(Product product, Integer quantity) {
        if(PROMOTION_DISCOUNT_PRODUCT_CODE.equals(product.getCode()) && quantity >= PROMOTION_PRODUCT_DISCOUNT_QUANTITY) {
            return product.getPrice().subtract(BigDecimal.valueOf(PROMOTION_PRODUCT_DISCOUNT).multiply(BigDecimal.valueOf(quantity)));
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
