package com.example.orderservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.orderservice.dto.UserDTO;

@Component
public class UserClient {
	
	private final RestClient client;
	
	UserClient(RestClient.Builder builder){
		this.client = builder
				.baseUrl("http://localhost:8081")
				.build();
	}

	public UserDTO getUser(String userId) {
		
		return client.get()
				.uri("/users/{id}", userId)
				.retrieve()
				.body(UserDTO.class);
	}
	
}
