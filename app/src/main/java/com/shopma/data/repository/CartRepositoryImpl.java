package com.shopma.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.shopma.data.local.DatabaseHelper;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.CartItem;
import com.shopma.domain.repository.CartRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepositoryImpl implements CartRepository {

    private final DatabaseHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public CartRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void getCart(DataCallback<List<CartItem>> callback) {
        executor.execute(() -> {
            List<CartItem> items = new ArrayList<>();
            for (DatabaseHelper.CartRow row : dbHelper.getCart()) {
                items.add(new CartItem(row.id, row.productId, row.title, row.price, row.quantity));
            }
            mainHandler.post(() -> callback.onSuccess(items));
        });
    }

    @Override
    public void addToCart(long productId, String title, double price, DataCallback<Void> callback) {
        executor.execute(() -> {
            dbHelper.addToCart(productId, title, price);
            mainHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void updateQuantity(long cartItemId, int quantity, DataCallback<Void> callback) {
        executor.execute(() -> {
            dbHelper.updateQuantity(cartItemId, quantity);
            mainHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void removeItem(long cartItemId, DataCallback<Void> callback) {
        executor.execute(() -> {
            dbHelper.removeItem(cartItemId);
            mainHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void clearCart(DataCallback<Void> callback) {
        executor.execute(() -> {
            dbHelper.clearCart();
            mainHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public int getItemCount() {
        return dbHelper.getCartItemCount();
    }
}
