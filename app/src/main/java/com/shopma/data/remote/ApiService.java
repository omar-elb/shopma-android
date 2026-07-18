package com.shopma.data.remote;

import com.shopma.data.remote.dto.*;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    @POST("api/auth/login")
    Call<AuthResponseDto> login(@Body LoginRequestDto request);

    @POST("api/auth/register")
    Call<AuthResponseDto> register(@Body RegisterRequestDto request);

    @GET("api/products")
    Call<List<ProductDto>> getProducts();

    @GET("api/products/categories")
    Call<List<String>> getCategories();

    @GET("api/products/category/{cat}")
    Call<List<ProductDto>> getProductsByCategory(@Path("cat") String category);

    @GET("api/products/{id}")
    Call<ProductDto> getProductById(@Path("id") long id);

    @GET("api/profile")
    Call<ProfileDto> getProfile();

    @PUT("api/profile")
    Call<ProfileDto> updateProfile(@Body UpdateProfileRequestDto request);

    @HTTP(method = "DELETE", path = "api/profile", hasBody = true)
    Call<Void> deleteAccount(@Body DeleteAccountRequestDto request);

    @GET("api/pickup-points")
    Call<List<PickupPointDto>> getPickupPoints();
}
