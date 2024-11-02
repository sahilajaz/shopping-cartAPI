package com.cart.web.cart_web.repository;

import com.cart.web.cart_web.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart , Long> {
    void deleteAllCartById(Long id);
}
