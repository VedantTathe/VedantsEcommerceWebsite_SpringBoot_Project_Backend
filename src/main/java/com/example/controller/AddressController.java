package com.example.controller;

import com.example.entity.Address;
import com.example.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Save a new address for a specific user (userId in the URL)
    @PostMapping("/user/{userId}")
    public ResponseEntity<Long> saveAddressForUser(
            @PathVariable Long userId,
            @RequestBody Address address) {
        address.setUserId(userId);  // Set the userId from the URL
        Address savedAddress = addressService.saveAddress(address);
        return ResponseEntity.ok(savedAddress.getId());
    }

    // Get all addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    // Get address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    // Get addresses by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }
}
