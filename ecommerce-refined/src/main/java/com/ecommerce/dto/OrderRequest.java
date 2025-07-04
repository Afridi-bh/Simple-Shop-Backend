package com.ecommerce.dto;

import com.ecommerce.entity.Payment;

public class OrderRequest {
    private Long userId;
    private String shippingAddress;
    private Payment.PaymentMethod paymentMethod;

    // Default constructor
    public OrderRequest() {}

    // Constructor with parameters
    public OrderRequest(Long userId, String shippingAddress, Payment.PaymentMethod paymentMethod) {
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}