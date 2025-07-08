package com.ecommerce.service;

import com.ecommerce.entity.User;
import com.ecommerce.entity.Cart;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // Save user
        User savedUser = userRepository.save(user);

        // Create cart for the user
        Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        return savedUser;
    }

    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) { // In real app, use BCrypt
                return user;
            }
        }
        throw new RuntimeException("Invalid email or password!");
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User updateUser(Long id, User userDetails) {
        User user = findById(id);
        user.setName(userDetails.getName());
        user.setPhone(userDetails.getPhone());
        // Don't update email or password here for security
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}