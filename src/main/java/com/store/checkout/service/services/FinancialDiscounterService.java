package com.store.checkout.service.services;

import com.store.checkout.service.domain.Product;
import com.store.checkout.service.services.dtos.DiscountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinancialDiscounterService implements DiscounterService {

    public static final String PROMOTION_DISCOUNT_PRODUCT_CODE = "TSHIRT";
    public static final int PROMOTION_PRODUCT_DISCOUNT = 1;
    public static final int PROMOTION_PRODUCT_DISCOUNT_QUANTITY = 3;

    @Override
    public DiscountDto getDiscount(Product product, Integer quantity) {
        DiscountDto discount = new DiscountDto();
        if(PROMOTION_DISCOUNT_PRODUCT_CODE.equals(product.getCode()) && quantity >= PROMOTION_PRODUCT_DISCOUNT_QUANTITY) {
           return applyDiscount(product, quantity);
        } else {
            discount.setDiscounted(false);
            return discount;
        }
    }

    private DiscountDto applyDiscount(Product product, Integer quantity) {
        DiscountDto discount = new DiscountDto();
        discount.setProductTotalPrice(product.getPrice().subtract(BigDecimal.valueOf(PROMOTION_PRODUCT_DISCOUNT)
                .multiply(BigDecimal.valueOf(quantity))));
        discount.setDiscounted(true);
        return discount;
    }

}
