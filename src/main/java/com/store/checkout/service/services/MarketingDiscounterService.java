package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MarketingDiscounterService implements DiscounterService {

    public static final String PROMOTION_FREE_PRODUCT_CODE = "VOUCHER";

    @Override
    public BigDecimal getTotalPrice(Product product, Integer quantity) {
        if(PROMOTION_FREE_PRODUCT_CODE.equals(product.getCode()) && quantity % 2 == 0) {
            return product.getPrice().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING).multiply(BigDecimal.valueOf(quantity));
        }
        if((PROMOTION_FREE_PRODUCT_CODE.equals(product.getCode()) && quantity % 2 != 0)) {
            return product.getPrice().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING).multiply(BigDecimal.valueOf(quantity))
                    .add(BigDecimal.valueOf(2.5));
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
