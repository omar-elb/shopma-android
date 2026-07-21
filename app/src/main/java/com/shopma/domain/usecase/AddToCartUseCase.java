package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.repository.CartRepository;

public class AddToCartUseCase {
    private final CartRepository repository;
    public AddToCartUseCase(CartRepository repository) { this.repository = repository; }

    public void execute(long productId, String title, double price, DataCallback<Void> callback) {
        repository.addToCart(productId, title, price, callback);
    }
}
