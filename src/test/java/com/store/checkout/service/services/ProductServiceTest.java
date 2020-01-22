package com.store.checkout.service.services;

import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.ProductRepository;
import com.store.checkout.service.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private DefaultProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void whenGetProductReturnsProduct() {
        Product product = buildProduct();

        when(productRepository.findById(anyLong())).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProduct(1L);

        assertThat(result.getId(), is(product.getId()));
        assertThat(result.getCode(), is(product.getCode()));
        assertThat(result.getName(), is(product.getName()));
        assertThat(result.getPrice(), is(product.getPrice()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenGetProductThrowsResourceNotFoundException() {
        productService.getProduct(anyLong());
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .code("VOUCHER")
                .name("Cabify Voucher")
                .price(5.0)
                .build();
    }

}