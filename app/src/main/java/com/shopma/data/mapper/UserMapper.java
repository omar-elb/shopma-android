package com.shopma.data.mapper;

import com.shopma.data.remote.dto.AuthResponseDto;
import com.shopma.domain.model.User;

public class UserMapper {

    public static User fromAuthResponse(AuthResponseDto dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), null, null);
    }

    public static User fromProfileDto(com.shopma.data.remote.dto.ProfileDto dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), dto.getAddress(), dto.getPhotoUrl());
    }
}
