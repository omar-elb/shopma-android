package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;

public interface ProfileRepository {
    void getProfile(DataCallback<User> callback);
    void updateProfile(String name, String address, String photoUrl, DataCallback<User> callback);
    void deleteAccount(String password, DataCallback<Void> callback);
}
