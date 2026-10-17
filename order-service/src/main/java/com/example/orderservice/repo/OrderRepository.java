package com.example.orderservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.orderservice.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
