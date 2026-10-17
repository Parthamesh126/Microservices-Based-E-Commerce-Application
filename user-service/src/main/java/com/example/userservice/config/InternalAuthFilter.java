package com.example.userservice.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InternalAuthFilter extends OncePerRequestFilter {

	private static final String HEADER = "X-Internal-Service-Key";
	
	@Value("${internal.auth.secret}")
	private String internalSecret;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getRequestURI().startsWith("/internal/auth");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String suppliedSecret = request.getHeader(HEADER);
		
		if(suppliedSecret == null || 
				MessageDigest.isEqual(
						internalSecret.getBytes(StandardCharsets.UTF_8), 
						suppliedSecret.getBytes(StandardCharsets.UTF_8))) {
			
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized internal service request");
			
			return;
			
		}
		
		filterChain.doFilter(request, response);
	}

}
