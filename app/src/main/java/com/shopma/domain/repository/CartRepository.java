package com.shopma.domain.repository;

import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.CartItem;

import java.util.List;

public interface CartRepository {
    void getCart(DataCallback<List<CartItem>> callback);
    void addToCart(long productId, String title, double price, DataCallback<Void> callback);
    void updateQuantity(long cartItemId, int quantity, DataCallback<Void> callback);
    void removeItem(long cartItemId, DataCallback<Void> callback);
    void clearCart(DataCallback<Void> callback);
    int getItemCount();
}
