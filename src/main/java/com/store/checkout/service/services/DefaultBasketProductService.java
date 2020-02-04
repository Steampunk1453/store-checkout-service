package com.store.checkout.service.services;

import com.store.checkout.service.repositories.BasketProductRepository;
import com.store.checkout.service.domain.BasketProduct;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DefaultBasketProductService implements BasketProductService {

    private final BasketProductRepository basketProductRepository;

    public DefaultBasketProductService(BasketProductRepository basketProductRepository) {
        this.basketProductRepository = basketProductRepository;
    }

    @Override
    public BasketProduct save(BasketProduct basketProduct) {
        return basketProductRepository.save(basketProduct);
    }

}