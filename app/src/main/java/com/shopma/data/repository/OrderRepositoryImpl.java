package com.shopma.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.shopma.data.local.DatabaseHelper;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Order;
import com.shopma.domain.repository.OrderRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepositoryImpl implements OrderRepository {

    private final DatabaseHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public OrderRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void placeOrder(DataCallback<Order> callback) {
        executor.execute(() -> {
            List<DatabaseHelper.CartRow> cartRows = dbHelper.getCart();

            if (cartRows.isEmpty()) {
                mainHandler.post(() -> callback.onError("Le panier est vide"));
                return;
            }

            double total = 0;
            int nbArticles = 0;
            for (DatabaseHelper.CartRow row : cartRows) {
                total += row.price * row.quantity;
                nbArticles += row.quantity;
            }

            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());
            long orderId = dbHelper.addOrder(date, nbArticles, total, "en_cours");
            dbHelper.clearCart();

            Order order = new Order(orderId, date, nbArticles, total, "en_cours");
            mainHandler.post(() -> callback.onSuccess(order));
        });
    }

    @Override
    public void getOrderHistory(DataCallback<List<Order>> callback) {
        executor.execute(() -> {
            List<Order> orders = new ArrayList<>();
            for (DatabaseHelper.OrderRow row : dbHelper.getOrderHistory()) {
                orders.add(new Order(row.id, row.date, row.nbArticles, row.montantTotal, row.statut));
            }
            mainHandler.post(() -> callback.onSuccess(orders));
        });
    }
}
