package com.store.checkout.service;

import com.store.checkout.service.controllers.OrderController;
import com.store.checkout.service.domain.Basket;
import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.domain.OrderStatus;
import com.store.checkout.service.domain.Product;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StoreCheckoutServiceApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreCheckoutServiceApplicationIT {

    public static final String TEST_JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsIkNMQUlNX1RPS0VOIjoiUk9MRV9BRE1JTixST0xFX1VTRVIiLCJpYXQiOjE1Nzk3MzE5MTMsImlzcyI6IklTU1VFUiJ9.eqD2aR2M1SVvdHVvLYWdUpduZ-D3YRiWQpVKHaDxBBk";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private OrderController orderController;

    @Mock
    private BasketRepository basketRepository;

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(orderController)
                .isNotNull();
    }

    @Test
    public void whenCreateBasketApiCallReturnsBasket() {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + TEST_JWT_TOKEN);
        HttpEntity<OrderDto> request = new HttpEntity<>(buildOrderRequest(), headers);
        final ResponseEntity<Basket> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/baskets", request, Basket.class);
        var response = postResponse.getBody();
        when(basketRepository.getOne(anyLong())).thenReturn(buildBasket());

        Assertions.assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
        assertThat(response, hasProperty("id", is(1L)));
        assertThat(response, hasProperty("dateCreated", is(LocalDate.now())));
        assertThat(response, hasProperty("status", is("PAID")));
        assertThat(response.getBasketProducts(), hasItem(hasProperty("quantity", is(2))));
    }

    private OrderDto buildOrderRequest() {
        List<BasketDto> basket = new ArrayList<>();
        var orderDto = new OrderDto();
        var basketDto = new BasketDto();
        basketDto.setProduct(buildProduct());
        basketDto.setQuantity(2);
        basket.add(basketDto);
        orderDto.setBaskets(basket);
        return orderDto;
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .code("VOUCHER")
                .name("Voucher")
                .price(new BigDecimal(5.0))
                .build();
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

}