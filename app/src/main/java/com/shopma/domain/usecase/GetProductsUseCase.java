package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Product;
import com.shopma.domain.repository.ProductRepository;
import java.util.List;

public class GetProductsUseCase {

    private final ProductRepository repository;

    public GetProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public void execute(DataCallback<List<Product>> callback) {
        repository.getAllProducts(callback);
    }
}
