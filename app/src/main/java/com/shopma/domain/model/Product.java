package com.shopma.domain.model;

public class Product {
    private final long id;
    private final String title;
    private final double price;
    private final String description;
    private final String category;
    private final String imageUrl;
    private final double rating;

    public Product(long id, String title, double price, String description,
                   String category, String imageUrl, double rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public double getRating() { return rating; }
}
