package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Product;
import com.shopma.domain.repository.ProductRepository;
import java.util.List;

public class GetProductsByCategoryUseCase {
    private final ProductRepository repository;
    public GetProductsByCategoryUseCase(ProductRepository repository) { this.repository = repository; }

    public void execute(String category, DataCallback<List<Product>> callback) {
        repository.getProductsByCategory(category, callback);
    }
}
