package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;
import com.shopma.domain.repository.AuthRepository;

public class LoginUseCase {

    private final AuthRepository repository;

    public LoginUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, String password, DataCallback<User> callback) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            callback.onError("Tous les champs sont obligatoires");
            return;
        }
        if (password.length() < 6) {
            callback.onError("Le mot de passe doit contenir au moins 6 caractères");
            return;
        }
        repository.login(email.trim(), password, callback);
    }
}
