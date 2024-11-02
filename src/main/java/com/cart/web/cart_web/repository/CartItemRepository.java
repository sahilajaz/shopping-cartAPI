package com.cart.web.cart_web.repository;

import com.cart.web.cart_web.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {
    void deleteAllByCartId(Long id);

}
