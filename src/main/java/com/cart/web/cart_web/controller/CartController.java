package com.cart.web.cart_web.controller;

import com.cart.web.cart_web.excpetions.ResourceNotFoundException;
import com.cart.web.cart_web.model.Cart;
import com.cart.web.cart_web.response.ApiResponse;
import com.cart.web.cart_web.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("success" , cart));
        } catch (ResourceNotFoundException e) {
                return  ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(e.getMessage() , null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId , @RequestBody Cart cart) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart clear success" , null));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage() , null));
        }
    }

    @GetMapping("{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total price" , totalPrice));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage() , null));
        }
    }
}
