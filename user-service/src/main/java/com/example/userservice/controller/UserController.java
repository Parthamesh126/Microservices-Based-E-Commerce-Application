package com.example.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;
	
	UserController(UserService userService){
		this.service = userService;
	}
	
	@Value("${server.port}")
    private String port;
	
	@PostMapping()
	public UserResponse createUser(@Valid @RequestBody User user) {
		return service.createUser(user);
	}
	
	@GetMapping("/{id}")
	public UserResponse getUserById(@PathVariable String id) {
		log.info(
                "Request handled by User Service on port: "
                        + port
        );
		return service.getuser(id);
	}

	@GetMapping("/getAllUser")
	public List<UserResponse> getAllUser(){
		return service.getAllUsers();
	}
	
}
