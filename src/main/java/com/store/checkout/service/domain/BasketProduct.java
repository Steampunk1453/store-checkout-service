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

    public static final String PROMOTION_FREE_PRODUCT_CODE = "VOUCHER";
    public static final String PROMOTION_DISCOUNT_PRODUCT_CODE = "TSHIRT";
    public static final int PROMOTION_PRODUCT_DISCOUNT = 1;
    public static final int PROMOTION_PRODUCT_DISCOUNT_QUANTITY = 3;

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
