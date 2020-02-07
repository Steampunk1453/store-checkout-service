package com.store.checkout.service.controllers;

import com.store.checkout.service.domain.Product;
import com.store.checkout.service.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ROLE_USER')")
    public synchronized ResponseEntity<Product> save(@RequestBody Product product) {
        productService.save(product);
        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/products")
                .buildAndExpand(product.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
    }

}
