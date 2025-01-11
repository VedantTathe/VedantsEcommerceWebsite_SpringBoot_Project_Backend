package com.example.service;

import com.example.entity.Address;
import com.example.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // Save a new address
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    // Get all addresses
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Get address by ID
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found with ID: " + id));
    }

    // Get addresses by userId
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }
}
