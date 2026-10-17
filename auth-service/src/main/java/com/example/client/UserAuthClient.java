package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.config.InternalFeignConfig;
import com.example.dto.AuthValidationRequest;
import com.example.dto.AuthValidationResponse;

@FeignClient(name = "user-service", configuration = InternalFeignConfig.class)
public interface UserAuthClient {
	
	@PostMapping("/internal/auth/validate")
	public AuthValidationResponse validateCredentials(@RequestBody AuthValidationRequest request);

}
