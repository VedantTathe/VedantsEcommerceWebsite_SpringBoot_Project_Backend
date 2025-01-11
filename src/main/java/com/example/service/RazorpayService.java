//package com.example.service;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RazorpayService {
//
//    private RazorpayClient client;
//
//    public RazorpayService() throws Exception {
//        this.client = new RazorpayClient("YOUR_KEY_ID", "YOUR_SECRET_KEY");
//    }
//
//    public String createOrder(double amount) throws Exception {
//        JSONObject options = new JSONObject();
//        options.put("amount", amount * 100);  // Razorpay accepts amount in paise
//        options.put("currency", "INR");
//        options.put("receipt", "txn_123456");
//
//        Order order = client.Orders.create(options);
//        return order.toString();
//    }
//}
