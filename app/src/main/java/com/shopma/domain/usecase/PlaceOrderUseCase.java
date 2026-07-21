package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Order;
import com.shopma.domain.repository.OrderRepository;

public class PlaceOrderUseCase {
    private final OrderRepository repository;
    public PlaceOrderUseCase(OrderRepository repository) { this.repository = repository; }

    public void execute(DataCallback<Order> callback) {
        repository.placeOrder(callback);
    }
}
