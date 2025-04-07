package com.order.service;

import java.util.List;

import com.order.model.Order;

public interface OrderService {
    Order placeOrder(Order order);
    Order updateOrderStatus(String id, String status);
    void cancelOrder(String id);
    Order getOrderById(String id);
    List<Order> getOrdersByUserId(String userId);
}