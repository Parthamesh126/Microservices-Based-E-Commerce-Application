package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderservice.dto.UserDTO;

//@FeignClient(name = "user-service", url = "http://localhost:8081")   // before registry with eureka we need url to request correct http request
@FeignClient(name = "user-service", fallback = UserClientFallback.class)
public interface UserClientOpenFeign {

	@GetMapping("/users/{id}")
	UserDTO getUser(@PathVariable String id);
	
}
