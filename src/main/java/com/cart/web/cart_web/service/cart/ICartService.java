package com.cart.web.cart_web.service.cart;

import com.cart.web.cart_web.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
