package com.store.checkout.service.repositories.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    private Double price;

}
