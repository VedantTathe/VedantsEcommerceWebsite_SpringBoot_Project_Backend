// UserService.java
package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.config.JwtResponse;
import com.example.config.JwtUtils;
import com.example.entity.LoginRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update a user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setRole(userDetails.getRole());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setMobile(userDetails.getMobile());
        user.setAddress(userDetails.getAddress());
        user.setGender(userDetails.getGender());
        return userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        // Dummy validation (Replace with DB validation)
    	List<User> list = getAllUsers();
    	for(User user: list)
    	{
    		System.out.println(user.getEmail()+ " "+ user.getPassword());
    		System.out.println(loginRequest.getEmail()+ " "+ loginRequest.getPassword());
    		if(user.getEmail().equals(loginRequest.getEmail()) && user.getPassword().equals(loginRequest.getPassword()))
    		{
    			String token = JwtUtils.generateToken(loginRequest.getEmail());
    			if(token==null)
    				return null;
    			
    			return new JwtResponse(token, user);
    		}
    	}
    	
        return null;
    }
}
