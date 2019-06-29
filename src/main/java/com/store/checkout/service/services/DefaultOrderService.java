package com.store.checkout.service.services;

import com.store.checkout.service.dtos.BasketDto;
import com.store.checkout.service.dtos.OrderRequest;
import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.repositories.domain.Basket;
import com.store.checkout.service.repositories.domain.BasketProduct;
import com.store.checkout.service.repositories.domain.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    private final ProductService productService;
    private final BasketService basketService;
    private final BasketProductService basketProductService;
    private final BasketRepository basketRepository;

    public DefaultOrderService(ProductService productService, BasketService basketService, BasketProductService basketProductService, BasketRepository basketRepository) {
        this.productService = productService;
        this.basketService = basketService;
        this.basketProductService = basketProductService;
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket saveBasket(OrderRequest orderRequest) {
        List<BasketDto> basketDto = orderRequest.getBasket();
        validateProductsExistence(basketDto);
        Basket basket = new Basket();
        basket.setStatus(OrderStatus.PAID.name());
        basket = basketService.create(basket);

        List<BasketProduct> basketProducts = new ArrayList<>();
        for (BasketDto dto : basketDto) {
            addProducts(basket, basketProducts, dto);
        }
        basket.setBasketProducts(basketProducts);

        basketService.update(basket);
        return basket;
    }

    @Override
    public Basket saveProduct(BasketDto basketDto) {
        Basket basket = basketRepository.getOne(basketDto.getBasketId());
        List<BasketProduct> basketProducts = new ArrayList<>();
        addProducts(basket, basketProducts, basketDto);
        basket.setBasketProducts(basketProducts);
        basketService.update(basket);
        return basket;
    }

    private void validateProductsExistence(List<BasketDto> basketDto) {
        List<BasketDto> list = basketDto
                .stream()
                .filter(os -> Objects.isNull(productService.getProduct(os
                        .getProduct()
                        .getId())))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }


    private void addProducts(Basket basket, List<BasketProduct> basketProducts, BasketDto dto) {
        basketProducts.add(basketProductService.create(new BasketProduct(basket, productService.getProduct(dto
                .getProduct()
                .getId()), dto.getQuantity())));
    }

}