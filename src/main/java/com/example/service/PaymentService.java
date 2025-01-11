package com.example.service;

import com.example.entity.Payment;
import com.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Fetch all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    // Create new payment
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Update existing payment
    public Payment updatePayment(Long id, Payment payment) {
        if (paymentRepository.existsById(id)) {
            payment.setId(id);
            return paymentRepository.save(payment);
        } else {
            throw new EmptyResultDataAccessException("Payment not found", 1);
        }
    }

    // Delete payment by ID
    public void deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
        } else {
            throw new EmptyResultDataAccessException("Payment not found", 1);
        }
    }
}
