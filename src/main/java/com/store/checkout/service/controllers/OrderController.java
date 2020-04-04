package com.store.checkout.service.controllers;

import com.store.checkout.service.domain.Basket;
import com.store.checkout.service.services.BasketService;
import com.store.checkout.service.services.OrderService;
import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;

    public OrderController(BasketService basketService, OrderService orderService) {
        this.basketService = basketService;
        this.orderService = orderService;
    }

    @PostMapping("/baskets")
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized ResponseEntity<Basket> save(@RequestBody OrderDto orderDto) {
        var basket = orderService.saveBasket(orderDto);
        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/baskets")
                .buildAndExpand(basket.getId())
                .toString();
        var headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
    }

    @PutMapping("/baskets")
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized ResponseEntity<Basket> update(@RequestBody BasketDto basketDto) {
        var basket = orderService.saveProduct(basketDto);
        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/baskets")
                .buildAndExpand(basket.getId())
                .toString();
        var headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/baskets/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized void delete(@PathVariable("basketId") long basketId) {
        basketService.delete(basketId);
    }

    @GetMapping(path = "/amounts/{basketId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized @NotNull BigDecimal getAmount(@PathVariable("basketId") long basketId) {
        return basketService.getTotalAmount(basketId);
    }

}
