package com.store.checkout.service.services;

import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.ProductRepository;
import com.store.checkout.service.domain.Product;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

}