package com.example.repository;

import com.example.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Custom query method to find addresses by userId
    List<Address> findByUserId(Long userId);
}
