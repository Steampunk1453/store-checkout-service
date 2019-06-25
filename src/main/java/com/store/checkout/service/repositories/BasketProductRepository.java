package com.store.checkout.service.repositories;

import com.store.checkout.service.repositories.domain.BasketProduct;
import com.store.checkout.service.repositories.domain.BasketProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketProductRepository extends JpaRepository<BasketProduct, BasketProductPK>  {
}
