package com.example.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.userservice.enums.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class User {

	@Id
	private String id;
	
	private String name;
	 
	private String email;
	
	private String password;
	
	private Roles role;
	
	private boolean enabled = true;
	
}
