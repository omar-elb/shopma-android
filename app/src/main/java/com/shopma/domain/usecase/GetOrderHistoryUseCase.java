package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Order;
import com.shopma.domain.repository.OrderRepository;
import java.util.List;

public class GetOrderHistoryUseCase {
    private final OrderRepository repository;
    public GetOrderHistoryUseCase(OrderRepository repository) { this.repository = repository; }

    public void execute(DataCallback<List<Order>> callback) {
        repository.getOrderHistory(callback);
    }
}
