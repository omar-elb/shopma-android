package com.shopma.data.repository;

import com.shopma.data.mapper.ProductMapper;
import com.shopma.data.remote.ApiService;
import com.shopma.data.remote.dto.ProductDto;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Product;
import com.shopma.domain.repository.ProductRepository;
import com.shopma.data.remote.ApiErrorParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final ApiService apiService;

    public ProductRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getAllProducts(DataCallback<List<Product>> callback) {
        apiService.getProducts().enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(ProductMapper.toDomainList(response.body()));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void getProductsByCategory(String category, DataCallback<List<Product>> callback) {
        apiService.getProductsByCategory(category).enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(ProductMapper.toDomainList(response.body()));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void getCategories(DataCallback<List<String>> callback) {
        apiService.getCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }
}
