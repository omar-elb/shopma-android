package com.shopma.data.remote.dto;

public class AuthResponseDto {
    private String token;
    private long id;
    private String name;
    private String email;

    public String getToken() { return token; }
    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
