package com.eShop.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eShop.domain.CartProduct;


public interface CartProductRepository extends CrudRepository<CartProduct, Long> {

}
