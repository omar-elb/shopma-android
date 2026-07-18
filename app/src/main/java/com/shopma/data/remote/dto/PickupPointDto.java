package com.shopma.data.remote.dto;

public class PickupPointDto {
    private long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
