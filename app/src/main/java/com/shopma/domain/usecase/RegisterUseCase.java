package com.shopma.domain.usecase;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;
import com.shopma.domain.repository.AuthRepository;

public class RegisterUseCase {

    private final AuthRepository repository;

    public RegisterUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void execute(String name, String email, String password, DataCallback<User> callback) {
        if (name == null || name.trim().isEmpty()) {
            callback.onError("Le nom est obligatoire");
            return;
        }
        if (email == null || email.trim().isEmpty()) {
            callback.onError("L'email est obligatoire");
            return;
        }
        if (password == null || password.length() < 6) {
            callback.onError("Le mot de passe doit contenir au moins 6 caractères");
            return;
        }
        repository.register(name.trim(), email.trim(), password, callback);
    }
}