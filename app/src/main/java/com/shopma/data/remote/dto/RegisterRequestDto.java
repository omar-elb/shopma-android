package com.shopma.data.remote.dto;

public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;

    public RegisterRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
