package com.store.checkout.service.repositories.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "basketProducts")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private String status;

    @OneToMany(mappedBy = "pk.basket", cascade = CascadeType.ALL)
    @Valid
    private List<BasketProduct> basketProducts;

    @Transient
    public Double getTotalAmount() {
        double sum = 0D;
        List<BasketProduct> basketProducts = getBasketProducts();
        for (BasketProduct bp : basketProducts) {
            sum += bp.getTotalPrice();
        }
        return sum;
    }

}
