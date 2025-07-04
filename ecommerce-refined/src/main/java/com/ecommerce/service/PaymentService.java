package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    public Payment processPayment(Long orderId, Payment.PaymentMethod paymentMethod) {
        Order order = orderService.getOrderById(orderId);

        // Create payment
        Payment payment = new Payment(order, order.getTotalAmount(), paymentMethod);
        payment.setTransactionId(generateTransactionId());

        try {
            // Simulate payment processing
            boolean paymentSuccess = simulatePaymentProcessing(paymentMethod, order.getTotalAmount());

            if (paymentSuccess) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
                payment.setPaymentDate(LocalDateTime.now());

                // Update order status
                orderService.updateOrderStatus(orderId, Order.OrderStatus.CONFIRMED);
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
            }

        } catch (Exception e) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
        }

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrder_Id(orderId);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
    }

    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new RuntimeException("Payment not found with transaction id: " + transactionId));
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    public Payment refundPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);

        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new RuntimeException("Cannot refund payment that is not completed!");
        }

        // Simulate refund processing
        payment.setStatus(Payment.PaymentStatus.REFUNDED);

        // Update order status
        orderService.updateOrderStatus(payment.getOrder().getId(), Order.OrderStatus.CANCELLED);

        return paymentRepository.save(payment);
    }

    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private boolean simulatePaymentProcessing(Payment.PaymentMethod method, Double amount) {
        // Simulate payment processing logic
        // In real application, integrate with payment gateways

        try {
            Thread.sleep(1000); // Simulate processing time

            // Simulate success rate based on payment method
            switch (method) {
                case CREDIT_CARD:
                case DEBIT_CARD:
                    return Math.random() > 0.05; // 95% success rate
                case PAYPAL:
                case MOBILE_BANKING:
                    return Math.random() > 0.02; // 98% success rate
                case BANK_TRANSFER:
                    return Math.random() > 0.01; // 99% success rate
                case CASH_ON_DELIVERY:
                    return true; // Always success for COD
                default:
                    return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}