package com.store.checkout.service.controllers;

import com.store.checkout.service.dtos.BasketDto;
import com.store.checkout.service.dtos.OrderRequest;
import com.store.checkout.service.repositories.domain.Basket;
import com.store.checkout.service.services.BasketService;
import com.store.checkout.service.services.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;
    private final OrderService orderService;

    public BasketController(BasketService basketService, OrderService orderService) {
        this.basketService = basketService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/amounts/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    public synchronized @NotNull Double getAmount(@PathVariable("basketId") long basketId) {
        return basketService.getTotalAmount(basketId);
    }

    @PostMapping
    public synchronized ResponseEntity<Basket> create(@RequestBody OrderRequest orderRequest) {
        Basket basket = orderService.saveBasket(orderRequest);
        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/baskets")
                .buildAndExpand(basket.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
    }

    @PostMapping(path = "/products")
    public synchronized ResponseEntity<Basket> add(@RequestBody BasketDto basketDto) {
        Basket basket = orderService.saveProduct(basketDto);
        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/products")
                .buildAndExpand(basket.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    public synchronized void delete(@PathVariable("basketId") long basketId) {
        basketService.delete(basketId);
    }

}
