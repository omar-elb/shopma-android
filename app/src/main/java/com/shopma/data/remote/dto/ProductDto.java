package com.shopma.data.remote.dto;

public class ProductDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private double rating;

    public long getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
    public double getRating() { return rating; }
}
