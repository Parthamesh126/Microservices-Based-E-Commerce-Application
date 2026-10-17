package com.example.orderservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "order")
@Getter
@Setter
public class Order {

	@Id
	private String id;
	
	private String userId;
	
	private String product;
	
	private double amount;
	
}
