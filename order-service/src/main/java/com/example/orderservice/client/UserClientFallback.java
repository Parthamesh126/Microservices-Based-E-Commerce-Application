package com.example.orderservice.client;

import org.springframework.stereotype.Component;

import com.example.orderservice.dto.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserClientFallback implements UserClientOpenFeign {

	@Override
	public UserDTO getUser(String id) {
		log.error("User Service unavailable for userId: {}",id);

        return null;
	}

}
