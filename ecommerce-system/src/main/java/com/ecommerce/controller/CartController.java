package com.ecommerce.controller;

import com.ecommerce.entity.CartItem;
import com.ecommerce.dto.CartRequest;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // API 6: POST /api/cart/add - Add item to cart
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequest cartRequest) {
        try {
            CartItem cartItem = cartService.addItemToCart(
                cartRequest.getUserId(), 
                cartRequest.getProductId(), 
                cartRequest.getQuantity()
            );
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }

    // API 7: GET /api/cart/{userId} - Get user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable Long userId) {
        try {
            List<CartItem> cartItems = cartService.getCartItems(userId);
            Double total = cartService.getCartTotal(userId);

            // Create response object
            CartResponse response = new CartResponse(cartItems, total);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get cart: " + e.getMessage());
        }
    }

    // API 8: DELETE /api/cart/item/{itemId} - Remove item from cart
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long itemId) {
        try {
            cartService.removeItemFromCart(itemId);
            return ResponseEntity.ok("Item removed from cart successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove item: " + e.getMessage());
        }
    }

    // Inner class for cart response
    public static class CartResponse {
        private List<CartItem> items;
        private Double total;

        public CartResponse(List<CartItem> items, Double total) {
            this.items = items;
            this.total = total;
        }

        public List<CartItem> getItems() { return items; }
        public void setItems(List<CartItem> items) { this.items = items; }
        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }
}