package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUser_Id(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
    }

    public CartItem addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(productId);

        // Check if product is in stock
        if (!productService.isInStock(productId, quantity)) {
            throw new RuntimeException("Product is out of stock!");
        }

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), productId);

        if (existingItem.isPresent()) {
            // Update quantity
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cart.setUpdatedDate(LocalDateTime.now());
            cartRepository.save(cart);
            return cartItemRepository.save(cartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem(cart, product, quantity, product.getPrice());
            cart.setUpdatedDate(LocalDateTime.now());
            cartRepository.save(cart);
            return cartItemRepository.save(cartItem);
        }
    }

    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));

        Cart cart = cartItem.getCart();
        cart.setUpdatedDate(LocalDateTime.now());
        cartRepository.save(cart);

        cartItemRepository.delete(cartItem);
    }

    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));

        if (quantity <= 0) {
            removeItemFromCart(cartItemId);
            return null;
        }

        // Check stock
        if (!productService.isInStock(cartItem.getProduct().getId(), quantity)) {
            throw new RuntimeException("Insufficient stock!");
        }

        cartItem.setQuantity(quantity);
        Cart cart = cartItem.getCart();
        cart.setUpdatedDate(LocalDateTime.now());
        cartRepository.save(cart);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cartItemRepository.findByCart_Id(cart.getId());
    }

    public Double getCartTotal(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        return cartItems.stream()
            .mapToDouble(CartItem::getTotalPrice)
            .sum();
    }

    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCart_Id(cart.getId());
        cartItemRepository.deleteAll(cartItems);
        cart.setUpdatedDate(LocalDateTime.now());
        cartRepository.save(cart);
    }
}