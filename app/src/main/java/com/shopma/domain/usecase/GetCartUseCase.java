package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.CartItem;
import com.shopma.domain.repository.CartRepository;
import java.util.List;

public class GetCartUseCase {
    private final CartRepository repository;
    public GetCartUseCase(CartRepository repository) { this.repository = repository; }

    public void execute(DataCallback<List<CartItem>> callback) {
        repository.getCart(callback);
    }
}
