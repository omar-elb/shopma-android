package com.shopma.domain.model;

public class User {
    private final long id;
    private final String name;
    private final String email;
    private final String address;
    private final String photoUrl;

    public User(long id, String name, String email, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhotoUrl() { return photoUrl; }
}
