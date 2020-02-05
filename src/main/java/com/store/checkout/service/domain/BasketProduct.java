package com.store.checkout.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BasketProduct {

    @EmbeddedId
    @JsonIgnore
    private BasketProductPK pk;

    @Column(nullable = false)
    private Integer quantity;

    public BasketProduct(Basket basket, Product product, Integer quantity) {
        pk = new BasketProductPK();
        pk.setBasket(basket);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

}
