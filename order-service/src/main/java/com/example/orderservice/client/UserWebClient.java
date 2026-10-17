package com.example.orderservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.dto.UserDTO;

import reactor.core.publisher.Mono;

@Component
public class UserWebClient {
	
	private final WebClient webClient;

    public UserWebClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

	
	public Mono<UserDTO> getUser(String userId){
		
		return webClient.get()
				.uri("/users/{id}", userId)
				.retrieve()
				.bodyToMono(UserDTO.class);
	}
	
}
