package com.store.checkout.service;

import com.store.checkout.service.controllers.BasketController;
import com.store.checkout.service.domain.Basket;
import com.store.checkout.service.domain.BasketProduct;
import com.store.checkout.service.domain.OrderStatus;
import com.store.checkout.service.domain.Product;
import com.store.checkout.service.repositories.BasketRepository;
import com.store.checkout.service.services.dtos.BasketDto;
import com.store.checkout.service.services.dtos.OrderRequest;
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
public class StoreCheckoutServiceApplicationIntegrationTest {

    public static final String TEST_JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsIkNMQUlNX1RPS0VOIjoiUk9MRV9BRE1JTixST0xFX1VTRVIiLCJpYXQiOjE1Nzk2NDY4MzIsImlzcyI6IklTU1VFUiIsImV4cCI6MTU3OTY3NTYzMn0.Q6VnR2JEbDugdH8hnR276-2R5aQq2wpPOgq7bdOv994";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private BasketController basketController;

    @Mock
    private BasketRepository basketRepository;

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(basketController)
                .isNotNull();
    }


    @Test
    public void whenCreateBasketApiCallReturnsBasket() {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + TEST_JWT_TOKEN);

        HttpEntity<OrderRequest> request = new HttpEntity<>(buildOrderRequest(), headers);

        final ResponseEntity<Basket> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/baskets", request, Basket.class);

        Basket response = postResponse.getBody();

        Assertions.assertThat(postResponse.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

        when(basketRepository.getOne(anyLong())).thenReturn(buildBasket());

        assertThat(response, hasProperty("id", is(1L)));
        assertThat(response, hasProperty("dateCreated", is(LocalDate.now())));
        assertThat(response, hasProperty("status", is("PAID")));
        assertThat(response.getBasketProducts(), hasItem(hasProperty("quantity", is(2))));
    }

    private OrderRequest buildOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        List<BasketDto> basket = new ArrayList<>();
        BasketDto basketDto = new BasketDto();
        basketDto.setProduct(buildProduct());
        basketDto.setQuantity(2);
        basket.add(basketDto);
        orderRequest.setBasket(basket);

        return orderRequest;
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .code("VOUCHER")
                .name("Cabify Voucher")
                .price(5.0)
                .build();
    }

    private Basket buildBasket() {
        List<BasketProduct> basketProducts = new ArrayList<>();
        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setQuantity(new Integer(2));
        basketProducts.add(basketProduct);

        return Basket.builder()
                .id(1L)
                .dateCreated(LocalDate.now())
                .status(OrderStatus.PAID.name())
                .basketProducts(basketProducts)
                .build();
    }

}