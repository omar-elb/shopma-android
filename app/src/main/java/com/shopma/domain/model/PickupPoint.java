package com.shopma.domain.model;

public class PickupPoint {
    private final long id;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;

    public PickupPoint(long id, String name, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
