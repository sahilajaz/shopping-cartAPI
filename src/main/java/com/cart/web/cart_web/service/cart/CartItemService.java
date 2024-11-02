package com.cart.web.cart_web.service.cart;

import com.cart.web.cart_web.excpetions.ResourceNotFoundException;
import com.cart.web.cart_web.model.Cart;
import com.cart.web.cart_web.model.CartItem;
import com.cart.web.cart_web.model.Product;
import com.cart.web.cart_web.repository.CartItemRepository;
import com.cart.web.cart_web.repository.CartRepository;
import com.cart.web.cart_web.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else  {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId , productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(long cartId, Long productId, int quantity) {
         Cart cart = cartService.getCart(cartId);
         cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                 .findFirst().ifPresent(item -> {
                     item.setQuantity(quantity);
                     item.setUnitPrice(item.getProduct().getPrice());
                     item.setTotalPrice();
                 });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
         Cart cart = cartService.getCart(cartId);

         return cart.getItems().stream()
                 .filter(item -> item.getProduct().getId().equals(productId))
                 .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
