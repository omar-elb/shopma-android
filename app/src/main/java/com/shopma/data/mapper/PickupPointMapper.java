package com.shopma.data.mapper;

import com.shopma.data.remote.dto.PickupPointDto;
import com.shopma.domain.model.PickupPoint;
import java.util.ArrayList;
import java.util.List;

public class PickupPointMapper {
    public static PickupPoint toDomain(PickupPointDto dto) {
        return new PickupPoint(dto.getId(), dto.getName(), dto.getAddress(), dto.getLatitude(), dto.getLongitude());
    }
    public static List<PickupPoint> toDomainList(List<PickupPointDto> dtos) {
        List<PickupPoint> points = new ArrayList<>();
        for (PickupPointDto dto : dtos) points.add(toDomain(dto));
        return points;
    }
}
