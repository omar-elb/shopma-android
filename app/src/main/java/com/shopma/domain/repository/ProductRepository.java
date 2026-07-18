package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    void getAllProducts(DataCallback<List<Product>> callback);
    void getProductsByCategory(String category, DataCallback<List<Product>> callback);
    void getCategories(DataCallback<List<String>> callback);
}
