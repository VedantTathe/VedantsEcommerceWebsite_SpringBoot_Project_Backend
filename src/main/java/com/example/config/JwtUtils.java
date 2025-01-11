package com.example.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;
//
//	public String generateToken(String username) {
//	    return Jwts.builder()
//	            .setSubject(username)
//	            .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
//	            .compact();
//	}
//
    private static final SecretKey SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256);

    
	public static String generateToken(String username) {
	    return Jwts.builder()
	            .setSubject(username)
	            .signWith(SECRET_KEY)  // Using the secure key
	            .compact();
	}
}
