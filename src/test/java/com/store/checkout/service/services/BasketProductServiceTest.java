package com.store.checkout.service.services;

import com.store.checkout.service.repositories.BasketProductRepository;
import com.store.checkout.service.domain.BasketProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketProductServiceTest {

    @InjectMocks
    private DefaultBasketProductService basketProductService;

    @Mock
    private BasketProductRepository basketProductRepository;

    @Test
    public void whenCreateReturnsBasketProduct() {
        BasketProduct basketProduct = buildBasketProduct();

        when(basketProductRepository.save(any(BasketProduct.class))).thenReturn(basketProduct);

        BasketProduct result = basketProductService.save(new BasketProduct());

        assertThat(result.getQuantity(), is(basketProduct.getQuantity()));
    }

    private BasketProduct buildBasketProduct() {
        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setQuantity(3);
        return basketProduct;
    }
}