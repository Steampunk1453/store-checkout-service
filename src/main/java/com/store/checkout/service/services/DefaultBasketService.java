package com.store.checkout.service.services;

import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.repositories.domain.Basket;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class DefaultBasketService implements BasketService {

    private final BasketRepository basketRepository;

    public DefaultBasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket create(Basket basket) {
        basket.setDateCreated(LocalDate.now());
        return basketRepository.save(basket);
    }

    @Override
    public void update(Basket basket) {
        basketRepository.save(basket);
    }

    @Override
    public Double getTotalAmount(long basketId) {
        Basket basket = basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));
        return basket.getTotalAmount();
    }

    @Override
    public void delete(long basketId) {
        basketRepository
                .findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket not found with id " + basketId));
        basketRepository.deleteById(basketId);
    }
}
