package com.shopma.presentation.panier;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.shopma.R;
import com.shopma.data.local.DatabaseHelper;
import com.shopma.data.local.SessionManager;
import com.shopma.data.repository.CartRepositoryImpl;
import com.shopma.data.repository.OrderRepositoryImpl;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.CartItem;
import com.shopma.domain.model.Order;
import com.shopma.domain.repository.CartRepository;
import com.shopma.domain.repository.OrderRepository;
import com.shopma.domain.usecase.GetCartUseCase;
import com.shopma.domain.usecase.PlaceOrderUseCase;
import com.shopma.presentation.common.HeaderFragment;
import com.shopma.util.NotificationHelper;

import java.util.List;
import java.util.Locale;

public class PanierActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvEmptyCart, tvTotal;
    private Button btnPasserCommande;
    private HeaderFragment headerFragment;

    private CartRepository cartRepository;
    private GetCartUseCase getCartUseCase;
    private PlaceOrderUseCase placeOrderUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        listView = findViewById(R.id.listViewPanier);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        tvTotal = findViewById(R.id.tvTotal);
        btnPasserCommande = findViewById(R.id.btnPasserCommande);

        SessionManager sessionManager = new SessionManager(this);
        headerFragment = HeaderFragment.newInstance(sessionManager.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.headerContainer, headerFragment)
                .commit();

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        cartRepository = new CartRepositoryImpl(dbHelper);
        OrderRepository orderRepository = new OrderRepositoryImpl(dbHelper);
        getCartUseCase = new GetCartUseCase(cartRepository);
        placeOrderUseCase = new PlaceOrderUseCase(orderRepository);

        btnPasserCommande.setOnClickListener(v -> placeOrder());
        NotificationHelper.createNotificationChannel(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        getCartUseCase.execute(new DataCallback<List<CartItem>>() {
            @Override
            public void onSuccess(List<CartItem> items) {
                headerFragment.updateCartCount(cartRepository.getItemCount());

                boolean isEmpty = items.isEmpty();
                listView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                tvEmptyCart.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
                btnPasserCommande.setEnabled(!isEmpty);

                double total = 0;
                for (CartItem item : items) total += item.getTotal();
                tvTotal.setText(String.format(Locale.getDefault(), "%.2f$", total));

                listView.setAdapter(new PanierAdapter(PanierActivity.this, items,
                        PanierActivity.this::decreaseQuantity));
            }
            @Override
            public void onError(String message) {
                Toast.makeText(PanierActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void decreaseQuantity(CartItem item) {
        DataCallback<Void> callback = new DataCallback<Void>() {
            @Override
            public void onSuccess(Void unused) { loadCart(); }
            @Override
            public void onError(String message) {
                Toast.makeText(PanierActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };

        if (item.getQuantity() <= 1) {
            cartRepository.removeItem(item.getId(), callback);
        } else {
            cartRepository.updateQuantity(item.getId(), item.getQuantity() - 1, callback);
        }
    }

    private void placeOrder() {
        btnPasserCommande.setEnabled(false);
        placeOrderUseCase.execute(new DataCallback<Order>() {
            @Override
            public void onSuccess(Order order) {
                NotificationHelper.showOrderConfirmation(PanierActivity.this, order.getTotalAmount());
                Toast.makeText(PanierActivity.this, "Commande confirmée !", Toast.LENGTH_LONG).show();
                loadCart();
            }
            @Override
            public void onError(String message) {
                btnPasserCommande.setEnabled(true);
                Toast.makeText(PanierActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
