package com.example.orderservice.service;

import org.springframework.stereotype.Service;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;

@Service
public interface OrderService {

	public Order createOrder(Order order);
	
	public OrderResponse getOrder(String id);
	
}
