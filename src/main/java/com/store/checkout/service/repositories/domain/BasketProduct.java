package com.store.checkout.service.repositories.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Data
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

    @Transient
    public Double getTotalPrice() {
        if(PROMOTION_FREE_PRODUCT_CODE.equals(getProduct().getCode()) && getQuantity() % 2 == 0) {
            return getProduct().getPrice() / 2 * getQuantity();
        }
        if((PROMOTION_FREE_PRODUCT_CODE.equals(getProduct().getCode()) && getQuantity() % 2 != 0)) {
            return (getProduct().getPrice() / 2) * getQuantity() + 2.5;
        }
        if(PROMOTION_DISCOUNT_PRODUCT_CODE.equals(getProduct().getCode()) && getQuantity() >= PROMOTION_PRODUCT_DISCOUNT_QUANTITY) {
            return (getProduct().getPrice() - PROMOTION_PRODUCT_DISCOUNT) * getQuantity();
        }
        return getProduct().getPrice() * getQuantity();
    }

}
