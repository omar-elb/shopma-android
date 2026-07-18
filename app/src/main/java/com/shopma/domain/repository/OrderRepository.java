package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Order;

import java.util.List;

public interface OrderRepository {
    void placeOrder(DataCallback<Order> callback);
    void getOrderHistory(DataCallback<List<Order>> callback);
}
