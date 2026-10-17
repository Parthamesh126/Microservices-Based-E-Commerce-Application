package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class InternalFeignConfig {
	
	@Bean
	public RequestInterceptor internalAuthInterceptor(@Value("internal.auth.secret") String secret) {
		return template -> template.header( "X-Internal-Service-Key", secret);
	}

}
