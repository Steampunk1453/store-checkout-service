package com.store.checkout.service.services;

import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.domain.Basket;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class DefaultBasketService implements BasketService {

    private final BasketRepository basketRepository;
    private final PriceCalculatorService priceCalculatorService;

    public DefaultBasketService(BasketRepository basketRepository, PriceCalculatorService priceCalculatorService) {
        this.basketRepository = basketRepository;
        this.priceCalculatorService = priceCalculatorService;
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
        var basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));

        return priceCalculatorService.calculateTotalPrice(basket.getBasketProducts());
    }

    @Override
    public void delete(long basketId) {
        basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));
        basketRepository.deleteById(basketId);
    }

}
