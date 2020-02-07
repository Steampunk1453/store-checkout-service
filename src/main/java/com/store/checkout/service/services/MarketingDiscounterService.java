package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;
import com.store.checkout.service.services.dtos.DiscountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MarketingDiscounterService implements DiscounterService {

    public static final String PROMOTION_FREE_PRODUCT_CODE = "VOUCHER";

    @Override
    public DiscountDto getDiscount(Product product, Integer quantity) {
        DiscountDto discount = new DiscountDto();
        if(PROMOTION_FREE_PRODUCT_CODE.equals(product.getCode())) {
            return applyDiscount(product, quantity);
        } else {
            discount.setDiscounted(false);
            return discount;
        }
    }

    private DiscountDto applyDiscount(Product product, Integer quantity) {
        DiscountDto discount = new DiscountDto();
        if(quantity % 2 == 0) {
            discount.setProductTotalPrice(product.getPrice().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING)
                    .multiply(BigDecimal.valueOf(quantity)));
            discount.setDiscounted(true);
        }
        if((quantity % 2 != 0)) {
            discount.setProductTotalPrice(product.getPrice().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING).multiply(BigDecimal.valueOf(quantity))
                    .add(BigDecimal.valueOf(2.5)));
            discount.setDiscounted(true);
        }
        return discount;
    }

}
