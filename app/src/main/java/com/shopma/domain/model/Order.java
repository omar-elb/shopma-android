package com.shopma.domain.model;

public class Order {
    private final long id;
    private final String date;
    private final int nbArticles;
    private final double totalAmount;
    private final String statut;

    public Order(long id, String date, int nbArticles, double totalAmount, String statut) {
        this.id = id;
        this.date = date;
        this.nbArticles = nbArticles;
        this.totalAmount = totalAmount;
        this.statut = statut;
    }

    public long getId() { return id; }
    public String getDate() { return date; }
    public int getNbArticles() { return nbArticles; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatut() { return statut; }
}
