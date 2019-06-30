package com.store.checkout.service.controllers;

import com.store.checkout.service.dtos.BasketDto;
import com.store.checkout.service.repositories.domain.Basket;
import com.store.checkout.service.services.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final OrderService orderService;

    public ProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Basket> add(@RequestBody BasketDto basketDto) {
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
}