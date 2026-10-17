package com.example.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService service;
	
	OrderController(OrderService orderService){
		this.service = orderService;
	}
	
	@PostMapping
	public Order createOrder(@Valid @RequestBody Order order) {
		
		Order response = service.createOrder(order);
		
		return response;
	}
	
	@GetMapping("/{id}")
	public OrderResponse getOrderById(@PathVariable String id) {
		OrderResponse response = service.getOrder(id);
		
		return response;
	}

}
