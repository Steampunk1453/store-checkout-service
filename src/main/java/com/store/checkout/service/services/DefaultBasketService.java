package com.store.checkout.service.services;

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
    public Iterable<Basket> getAllBaskets() {
        return this.basketRepository.findAll();
    }

    @Override
    public Basket create(Basket basket) {
        basket.setDateCreated(LocalDate.now());

        return this.basketRepository.save(basket);
    }

    @Override
    public void update(Basket basket) {
        this.basketRepository.save(basket);
    }
}
