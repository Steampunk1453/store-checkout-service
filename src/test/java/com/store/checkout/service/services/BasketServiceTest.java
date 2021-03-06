package com.store.checkout.service.services;

import com.store.checkout.service.exceptions.ResourceNotFoundException;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.domain.Basket;
import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.domain.OrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceTest {

    @InjectMocks
    private DefaultBasketService basketService;

    @Mock
    private BasketRepository basketRepository;

    @Test
    public void whenCreateReturnsBasket() {
        var basket = buildBasketResponse();
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        var result = basketService.save(buildBasket());

        assertThat(result.getId(), is(basket.getId()));
        assertThat(result.getDateCreated(), is(basket.getDateCreated()));
        assertThat(result.getStatus(), is(basket.getStatus()));
        assertThat(result.getBasketProducts().get(0).getQuantity(), is(basket.getBasketProducts().get(0).getQuantity()));
    }

    @Test
    public void whenUpdateCallRepository() {
        when(basketRepository.save(any(Basket.class))).thenReturn(new Basket());

        basketService.update(new Basket());

        verify(basketRepository, times(1)).save(any(Basket.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenGetTotalAmountThrowsResourceNotFoundException() {
        basketService.getTotalAmount(1L);

        verify(basketRepository, times(1)).findById(anyLong());
    }

    @Test
    public void whenDeleteCallRepository() {
        when(basketRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Basket()));

        basketService.delete(anyLong());

        verify(basketRepository, times(1)).deleteById(anyLong());
    }

    private Basket buildBasket() {
        List<BasketProduct> basketProducts = new ArrayList<>();
        var basketProduct = new BasketProduct();
        basketProduct.setQuantity(2);
        basketProducts.add(basketProduct);

        return Basket.builder()
                .status(OrderStatus.PAID.name())
                .basketProducts(basketProducts)
                .build();
    }


    private Basket buildBasketResponse() {
        List<BasketProduct> basketProducts = new ArrayList<>();
        var basketProduct = new BasketProduct();
        basketProduct.setQuantity(2);
        basketProducts.add(basketProduct);

        return Basket.builder()
                .id(1L)
                .dateCreated(LocalDate.now())
                .status(OrderStatus.PAID.name())
                .basketProducts(basketProducts)
                .build();
    }

}