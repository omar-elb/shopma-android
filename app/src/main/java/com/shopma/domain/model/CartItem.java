package com.shopma.domain.model;

public class CartItem {
    private final long id;
    private final long productId;
    private final String title;
    private final double price;
    private final int quantity;

    public CartItem(long id, long productId, String title, double price, int quantity) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() { return id; }
    public long getProductId() { return productId; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return price * quantity; }
}
