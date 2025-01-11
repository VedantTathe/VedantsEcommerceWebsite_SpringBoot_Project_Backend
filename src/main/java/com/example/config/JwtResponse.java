package com.example.config;

import com.example.entity.User;

public class JwtResponse {
    private String token;
    private User user;

    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
