package com.shopma.data.repository;

import com.shopma.data.local.SessionManager;
import com.shopma.data.mapper.UserMapper;
import com.shopma.data.remote.ApiErrorParser;
import com.shopma.data.remote.ApiService;
import com.shopma.data.remote.dto.AuthResponseDto;
import com.shopma.data.remote.dto.LoginRequestDto;
import com.shopma.data.remote.dto.RegisterRequestDto;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;
import com.shopma.domain.repository.AuthRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {

    private final ApiService apiService;
    private final SessionManager session;

    public AuthRepositoryImpl(ApiService apiService, SessionManager session) {
        this.apiService = apiService;
        this.session = session;
    }

    @Override
    public void login(String email, String password, DataCallback<User> callback) {
        apiService.login(new LoginRequestDto(email, password)).enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponseDto dto = response.body();
                    session.saveSession(dto.getToken(), dto.getId(), dto.getName(), dto.getEmail());
                    callback.onSuccess(UserMapper.fromAuthResponse(dto));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void register(String name, String email, String password, DataCallback<User> callback) {
        apiService.register(new RegisterRequestDto(name, email, password)).enqueue(new Callback<AuthResponseDto>() {
            @Override
            public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponseDto dto = response.body();
                    session.saveSession(dto.getToken(), dto.getId(), dto.getName(), dto.getEmail());
                    callback.onSuccess(UserMapper.fromAuthResponse(dto));
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }
            @Override
            public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                callback.onError("Connexion impossible : " + t.getMessage());
            }
        });
    }

    @Override
    public void logout() {
        session.logout();
    }

    @Override
    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }
}
