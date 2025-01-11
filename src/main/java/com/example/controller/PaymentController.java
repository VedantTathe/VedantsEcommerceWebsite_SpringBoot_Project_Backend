package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Payment;
import com.example.service.PaymentService;

import java.util.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.secret_key}")
    private String razorpaySecret;
    

    private final Map<String, Object> orderStore = new HashMap<>();
    private final Map<String, Object> paymentStore = new HashMap<>();

    @Autowired
    private PaymentService paymentService;
    

    // 1️⃣ Create Order (For Demo Only - You Can Modify This)
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("id", "order_" + System.currentTimeMillis());
        orderDetails.put("amount", data.get("amount"));
        orderDetails.put("currency", "INR");
        orderStore.put(orderDetails.get("id").toString(), orderDetails);

        return ResponseEntity.ok(orderDetails);
    }

    // 2️⃣ Get Order Details by ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        if (orderStore.containsKey(orderId)) {
            return ResponseEntity.ok(orderStore.get(orderId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    // 3️⃣ Save Payment Details
    @PostMapping("/save-payment")
    public ResponseEntity<?> savePaymentDetails(@RequestBody Map<String, Object> paymentDetails) {
        paymentStore.put(paymentDetails.get("paymentId").toString(), paymentDetails);
        return ResponseEntity.ok("Payment details saved successfully!");
    }

    // 4️⃣ Verify Payment
    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
        try {
            String paymentId = paymentDetails.get("paymentId");
            String orderId = paymentDetails.get("orderId");

            // Create payment verification URL
            String paymentVerificationUrl = "https://api.razorpay.com/v1/payments/" + paymentId;

            // Prepare the credentials (Base64 encoded key:secret)
            String auth = razorpayKey + ":" + razorpaySecret;

            // Call Razorpay API to verify the payment
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(paymentVerificationUrl, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> paymentResponse = response.getBody();
                String status = (String) paymentResponse.get("status");

                if ("authorized".equals(status)) {                	
                	return ResponseEntity.ok(true);
                    
                } else {
                    return ResponseEntity.ok(false);
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error verifying payment.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during payment verification.");
        }
    }

    // 5️⃣ Get All Payments
    @GetMapping("/allpayments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // 6️⃣ Delete Payment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok("Payment deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
    }
}
