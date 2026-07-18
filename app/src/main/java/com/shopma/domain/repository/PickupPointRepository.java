package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.PickupPoint;

import java.util.List;

public interface PickupPointRepository {
    void getPickupPoints(DataCallback<List<PickupPoint>> callback);
}
