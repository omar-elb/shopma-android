package com.shopma.data.repository;

import com.shopma.data.mapper.UserMapper;
import com.shopma.data.remote.ApiErrorParser;
import com.shopma.data.remote.ApiService;
import com.shopma.data.remote.dto.DeleteAccountRequestDto;
import com.shopma.data.remote.dto.ProfileDto;
import com.shopma.data.remote.dto.UpdateProfileRequestDto;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;
import com.shopma.domain.repository.ProfileRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final ApiService apiService;

    public ProfileRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getProfile(DataCallback<User> callback) {
        apiService.getProfile().enqueue(new Callback<ProfileDto>() {
            @Override
            public void onResponse(Call<ProfileDto> call, Response<ProfileDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(UserMapper.fromProfileDto(response.body()));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<ProfileDto> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void updateProfile(String name, String address, String photoUrl, DataCallback<User> callback) {
        apiService.updateProfile(new UpdateProfileRequestDto(name, address, photoUrl)).enqueue(new Callback<ProfileDto>() {
            @Override
            public void onResponse(Call<ProfileDto> call, Response<ProfileDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(UserMapper.fromProfileDto(response.body()));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<ProfileDto> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteAccount(String password, DataCallback<Void> callback) {
        apiService.deleteAccount(new DeleteAccountRequestDto(password)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) callback.onSuccess(null);
                else callback.onError(ApiErrorParser.parse(response));
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }
}
