package com.store.checkout.service.repositories.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Code is required")
    @Basic(optional = false)
    private String code;

    @NotNull(message = "Product name is required")
    @Basic(optional = false)
    private String name;

    private Double price;

}
