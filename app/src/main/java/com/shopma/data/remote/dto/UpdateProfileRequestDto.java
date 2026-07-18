package com.shopma.data.remote.dto;

public class UpdateProfileRequestDto {
    private String name;
    private String address;
    private String photoUrl;

    public UpdateProfileRequestDto(String name, String address, String photoUrl) {
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }
}
