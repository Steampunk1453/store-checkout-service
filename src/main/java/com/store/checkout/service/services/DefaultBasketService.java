package com.store.checkout.service.services;

import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.domain.Basket;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DefaultBasketService implements BasketService {

    private final BasketRepository basketRepository;
    private final MarketingDiscounterService marketingDiscounterService;
    private final FinancialDiscounterService financialDiscounterService;

    public DefaultBasketService(BasketRepository basketRepository, MarketingDiscounterService marketingDiscounterService, FinancialDiscounterService financialDiscounterService) {
        this.basketRepository = basketRepository;
        this.marketingDiscounterService = marketingDiscounterService;
        this.financialDiscounterService = financialDiscounterService;
    }

    @Override
    public Basket save(Basket basket) {
        basket.setDateCreated(LocalDate.now());
        return basketRepository.save(basket);
    }

    @Override
    public void update(Basket basket) {
        basketRepository.save(basket);
    }

    @Override
    public BigDecimal getTotalAmount(long basketId) {
        Basket basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));
       ;
        return calculateAmount(basket.getBasketProducts());
    }

    @Override
    public void delete(long basketId) {
        basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));
        basketRepository.deleteById(basketId);
    }

    private BigDecimal calculateAmount(List<BasketProduct> basketProducts) {
        BigDecimal sum = new BigDecimal(0);
        for (BasketProduct basketProduct : basketProducts) {
            sum = sum.add(marketingDiscounterService.getTotalPrice(basketProduct.getProduct(), basketProduct.getQuantity()));
            sum = sum.add(financialDiscounterService.getTotalPrice(basketProduct.getProduct(), basketProduct.getQuantity()));
        }
        return sum;
    }
}
