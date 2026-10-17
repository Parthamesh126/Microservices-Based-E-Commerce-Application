package com.example.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.dto.AuthValidationResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final SecretKey singingKey;
	
	private final long exiprationMS;
	
	public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.access-token-expiration-ms}") long exiprationMS) {
		
		this.singingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.exiprationMS = exiprationMS;
				
	}
	
	public String generateAccessToken(AuthValidationResponse user) {
		
		Date now = new Date();
		Date expiry = new Date(now.getTime() + exiprationMS);
		
		return Jwts.builder()
				.subject(user.id())
				.claim("email", user.email())
				.claim("role", user.role())
				.issuer("auth-service")
				.issuedAt(now)
				.expiration(expiry)
				.signWith(singingKey)
				.compact();
		
	}
	
	public long getExpirationMs() {
        return exiprationMS;
    }
}
