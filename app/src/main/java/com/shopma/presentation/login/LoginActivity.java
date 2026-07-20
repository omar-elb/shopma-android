package com.shopma.presentation.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shopma.R;
import com.shopma.data.local.SessionManager;
import com.shopma.data.remote.ApiClient;
import com.shopma.data.repository.AuthRepositoryImpl;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.User;
import com.shopma.domain.repository.AuthRepository;
import com.shopma.domain.usecase.LoginUseCase;
import com.shopma.domain.usecase.RegisterUseCase;
import com.shopma.presentation.accueil.AccueilActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "shopma_prefs";
    private static final String PREF_SAVED_EMAIL = "saved_email";

    private EditText etEmail, etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;
    private ProgressBar progressBar;

    private SessionManager sessionManager;
    private AuthRepository authRepository;
    private LoginUseCase loginUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            goToAccueil(sessionManager.getName());
            return;
        }

        bindViews();
        authRepository = new AuthRepositoryImpl(ApiClient.getService(this), sessionManager);
        loginUseCase = new LoginUseCase(authRepository);
        restoreSavedEmail();

        btnLogin.setOnClickListener(v -> attemptLogin());
        findViewById(R.id.tvRegisterLink).setOnClickListener(v -> showRegisterDialog());
    }

    private void bindViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    private void restoreSavedEmail() {
        String savedEmail = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(PREF_SAVED_EMAIL, null);
        if (savedEmail != null) {
            etEmail.setText(savedEmail);
            cbRememberMe.setChecked(true);
        }
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        setLoading(true);
        loginUseCase.execute(email, password, new DataCallback<User>() {
            @Override
            public void onSuccess(User user) {
                setLoading(false);
                saveEmailIfNeeded(email);
                goToAccueil(user.getName());
            }
            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showRegisterDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_register, null);
        EditText etName = dialogView.findViewById(R.id.etRegisterName);
        EditText etRegEmail = dialogView.findViewById(R.id.etRegisterEmail);
        EditText etRegPassword = dialogView.findViewById(R.id.etRegisterPassword);

        new AlertDialog.Builder(this)
                .setTitle(R.string.register_title)
                .setView(dialogView)
                .setPositiveButton(R.string.btn_register, (dialog, which) -> {
                    RegisterUseCase registerUseCase = new RegisterUseCase(authRepository);
                    setLoading(true);
                    registerUseCase.execute(
                            etName.getText().toString(),
                            etRegEmail.getText().toString(),
                            etRegPassword.getText().toString(),
                            new DataCallback<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    setLoading(false);
                                    goToAccueil(user.getName());
                                }
                                @Override
                                public void onError(String message) {
                                    setLoading(false);
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            });
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private void saveEmailIfNeeded(String email) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (cbRememberMe.isChecked()) prefs.edit().putString(PREF_SAVED_EMAIL, email).apply();
        else prefs.edit().remove(PREF_SAVED_EMAIL).apply();
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!loading);
    }

    private void goToAccueil(String userName) {
        Intent intent = new Intent(this, AccueilActivity.class);
        intent.putExtra(AccueilActivity.EXTRA_USER_NAME, userName);
        startActivity(intent);
        finish();
    }
}
