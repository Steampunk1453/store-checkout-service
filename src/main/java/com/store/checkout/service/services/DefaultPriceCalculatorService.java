package com.store.checkout.service.services;

import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.domain.Product;
import com.store.checkout.service.services.dtos.DiscountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultPriceCalculatorService implements PriceCalculatorService {

    private final MarketingDiscounterService marketingDiscounterService;
    private final FinancialDiscounterService financialDiscounterService;

    public DefaultPriceCalculatorService(MarketingDiscounterService marketingDiscounterService, FinancialDiscounterService financialDiscounterService) {
        this.marketingDiscounterService = marketingDiscounterService;
        this.financialDiscounterService = financialDiscounterService;
    }

    @Override
    public BigDecimal calculateTotalPrice(List<BasketProduct> basketProducts) {
        BigDecimal total = new BigDecimal(0);

        for (BasketProduct basketProduct : basketProducts) {

            Product product = basketProduct.getProduct();
            Integer quantity = basketProduct.getQuantity();

            DiscountDto marketingDiscount = marketingDiscounterService.getDiscount(product, quantity);
            DiscountDto financialDiscount = financialDiscounterService.getDiscount(product, quantity);

            if(marketingDiscount.isDiscounted()) {
                total = total.add(marketingDiscount.getProductTotalPrice());
            }
            if(financialDiscount.isDiscounted()) {
                total = total.add(financialDiscount.getProductTotalPrice());
            } else if(!marketingDiscount.isDiscounted() && !financialDiscount.isDiscounted()) {
                total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }

        }
        return total;
    }

}
