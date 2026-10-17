package com.example.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

	private String id;

    private String product;

    private Double amount;

    private UserDTO user;
    
}
