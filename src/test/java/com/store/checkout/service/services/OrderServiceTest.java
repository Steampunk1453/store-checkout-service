package com.store.checkout.service.services;

import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderDto;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.domain.Basket;
import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.domain.OrderStatus;
import com.store.checkout.service.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private DefaultOrderService orderService;

    @Mock
    private ProductService productService;

    @Mock
    private BasketService basketService;

    @Mock
    private BasketProductService basketProductService;

    @Mock
    private BasketRepository basketRepository;

    @Test
    public void whenSaveBasketReturnsBasket() {
        var orderDto = buildOrderRequest();
        var basket = buildBasket();
        var basketProduct = buildBasketProduct();
        Product product = buildProduct();
        when(basketService.save(any(Basket.class))).thenReturn(basket);
        when(basketProductService.save(any(BasketProduct.class))).thenReturn(basketProduct);
        when(productService.get(anyLong())).thenReturn(product);
        doNothing().when(basketService).update(any(Basket.class));

        var result = orderService.saveBasket(orderDto);

        assertThat(result.getId(), is(basket.getId()));
        assertThat(result.getDateCreated(), is(basket.getDateCreated()));
        assertThat(result.getStatus(), is(basket.getStatus()));
        assertThat(result.getBasketProducts().get(0).getQuantity(), is(basket.getBasketProducts().get(0).getQuantity()));
    }

    @Test
    public void whenSaveProductReturnsBasket() {
        var basketDto = buildBasketDto();
        var basket = buildBasket();
        var basketProduct = buildBasketProduct();
        var product = buildProduct();
        when(basketRepository.getOne(anyLong())).thenReturn(basket);
        when(basketProductService.save(any(BasketProduct.class))).thenReturn(basketProduct);
        when(productService.get(anyLong())).thenReturn(product);
        doNothing().when(basketService).update(any(Basket.class));

        var result = orderService.saveProduct(basketDto);

        assertThat(result.getId(), is(basket.getId()));
        assertThat(result.getDateCreated(), is(basket.getDateCreated()));
        assertThat(result.getStatus(), is(basket.getStatus()));
        assertThat(result.getBasketProducts().get(0).getQuantity(), is(basket.getBasketProducts().get(0).getQuantity()));
    }

    private OrderDto buildOrderRequest() {
        var orderDto = new OrderDto();
        List<BasketDto> basket = new ArrayList<>();
        var basketDto = new BasketDto();
        var product = Product.builder()
                .id(1L)
                .code("VOUCHER")
                .name("Voucher")
                .price(new BigDecimal("5.0"))
                .build();
        basketDto.setProduct(product);
        basketDto.setQuantity(2);
        basket.add(basketDto);
        orderDto.setBaskets(basket);

        return orderDto;
    }

    private Basket buildBasket() {
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

    private BasketProduct buildBasketProduct() {
        var basketProduct = new BasketProduct();
        basketProduct.setQuantity(3);
        return basketProduct;
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .code("VOUCHER")
                .name("Voucher")
                .price(new BigDecimal("5.0"))
                .build();
    }

    private BasketDto buildBasketDto() {
        return BasketDto.builder()
                .basketId(1L)
                .product(buildProduct())
                .quantity(3)
                .build();
    }

}