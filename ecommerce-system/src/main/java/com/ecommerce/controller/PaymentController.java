package com.ecommerce.controller;

import com.ecommerce.entity.Payment;
import com.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // API 11: POST /api/payments - Process payment
    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = paymentService.processPayment(
                paymentRequest.getOrderId(), 
                paymentRequest.getPaymentMethod()
            );
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment processing failed: " + e.getMessage());
        }
    }

    // Inner class for payment request
    public static class PaymentRequest {
        private Long orderId;
        private Payment.PaymentMethod paymentMethod;

        public PaymentRequest() {}

        public PaymentRequest(Long orderId, Payment.PaymentMethod paymentMethod) {
            this.orderId = orderId;
            this.paymentMethod = paymentMethod;
        }

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        public Payment.PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(Payment.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    }
}