package com.shopma.data.repository;

import com.shopma.data.mapper.PickupPointMapper;
import com.shopma.data.remote.ApiErrorParser;
import com.shopma.data.remote.ApiService;
import com.shopma.data.remote.dto.PickupPointDto;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.PickupPoint;
import com.shopma.domain.repository.PickupPointRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class PickupPointRepositoryImpl implements PickupPointRepository {

    private final ApiService apiService;

    public PickupPointRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getPickupPoints(DataCallback<List<PickupPoint>> callback) {
        apiService.getPickupPoints().enqueue(new Callback<List<PickupPointDto>>() {
            @Override
            public void onResponse(Call<List<PickupPointDto>> call, Response<List<PickupPointDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(PickupPointMapper.toDomainList(response.body()));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<List<PickupPointDto>> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }
}