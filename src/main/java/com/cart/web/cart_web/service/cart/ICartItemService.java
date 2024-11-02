package com.cart.web.cart_web.service.cart;

import com.cart.web.cart_web.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId , Long productId , int quantity);
    void removeItemFromCart(Long cartId , Long productId);
    void updateItemQuantity(long cartId , Long productId , int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
