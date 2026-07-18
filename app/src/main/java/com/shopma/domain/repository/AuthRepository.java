package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;

public interface AuthRepository {
    void login(String email, String password, DataCallback<User> callback);
    void register(String name, String email, String password, DataCallback<User> callback);
    void logout();
    boolean isLoggedIn();
}
