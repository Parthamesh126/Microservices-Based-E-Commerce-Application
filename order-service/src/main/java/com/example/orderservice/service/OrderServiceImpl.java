package com.example.orderservice.service;

import org.springframework.stereotype.Component;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.client.UserClientOpenFeign;
import com.example.orderservice.client.UserWebClient;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UserDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repo.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository repo;
	
	private final UserClient client;
	
	private final UserWebClient userWebClient;
	
	private final UserClientOpenFeign uerClientOpenFeign;
	
	OrderServiceImpl(OrderRepository orderRepo, UserClient userClient, UserWebClient userWebClient, UserClientOpenFeign uerClientOpenFeign){
		this.repo = orderRepo;
		this.client = userClient;
		this.userWebClient = userWebClient;
		this.uerClientOpenFeign = uerClientOpenFeign;
	}

	@Override
	public Order createOrder(Order order) {
		
		if(order.getUserId() == null || order.getProduct() == null) return null;
	
		Order save = repo.save(order);
		
		return save;
	}

	@Override
	public OrderResponse getOrder(String id) {
		
		Order order = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		
		if(order == null || order.getUserId() == null) return null;
		
//		UserDTO dto = client.getUser(order.getUserId());
//		Mono<UserDTO> dto = userWebClient.getUser(order.getUserId());
		
		UserDTO dto = uerClientOpenFeign.getUser(order.getUserId());
				
		OrderResponse response = new OrderResponse();
		
		response.setId(order.getId());
		response.setProduct(order.getProduct());
		response.setAmount(order.getAmount());
		response.setUser(dto);
		
		if (dto != null) {
		    log.info("Response with feign: {}", dto.getName());
		} else {
		    log.warn("User Service is unavailable. User information could not be fetched.");
		}		
		return response;
	}

}
