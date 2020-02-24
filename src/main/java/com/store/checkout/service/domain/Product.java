package com.store.checkout.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    private BigDecimal price;

}
