package com.shopma.presentation.catalogue;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.shopma.R;
import com.shopma.data.local.DatabaseHelper;
import com.shopma.data.local.SessionManager;
import com.shopma.data.remote.ApiClient;
import com.shopma.data.repository.CartRepositoryImpl;
import com.shopma.data.repository.ProductRepositoryImpl;
import com.shopma.domain.callback.DataCallback;
import com.shopma.domain.model.Product;
import com.shopma.domain.repository.CartRepository;
import com.shopma.domain.repository.ProductRepository;
import com.shopma.domain.usecase.AddToCartUseCase;
import com.shopma.domain.usecase.GetProductsByCategoryUseCase;
import com.shopma.domain.usecase.GetProductsUseCase;
import com.shopma.presentation.common.HeaderFragment;

import java.util.List;

public class CatalogueActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.shopma.EXTRA_CATEGORY";

    private ListView listView;
    private ProgressBar progressBar;
    private HeaderFragment headerFragment;
    private CartRepository cartRepository;
    private AddToCartUseCase addToCartUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        listView = findViewById(R.id.listViewProducts);
        progressBar = findViewById(R.id.progressBar);

        SessionManager sessionManager = new SessionManager(this);
        headerFragment = HeaderFragment.newInstance(sessionManager.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.headerContainer, headerFragment)
                .commit();

        cartRepository = new CartRepositoryImpl(DatabaseHelper.getInstance(this));
        addToCartUseCase = new AddToCartUseCase(cartRepository);

        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        headerFragment.updateCartCount(cartRepository.getItemCount());
    }

    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);
        ProductRepository productRepository = new ProductRepositoryImpl(ApiClient.getService(this));
        String category = getIntent().getStringExtra(EXTRA_CATEGORY);

        DataCallback<List<Product>> callback = new DataCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                progressBar.setVisibility(View.GONE);
                ProduitAdapter adapter = new ProduitAdapter(CatalogueActivity.this, products);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((parent, view, position, id) ->
                        addProductToCart(products.get(position)));
            }
            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CatalogueActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };

        if (category != null) {
            new GetProductsByCategoryUseCase(productRepository).execute(category, callback);
        } else {
            new GetProductsUseCase(productRepository).execute(callback);
        }
    }

    private void addProductToCart(Product product) {
        addToCartUseCase.execute(product.getId(), product.getTitle(), product.getPrice(),
                new DataCallback<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        headerFragment.updateCartCount(cartRepository.getItemCount());
                        Toast.makeText(CatalogueActivity.this,
                                product.getTitle() + " ajouté au panier", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(String message) {
                        Toast.makeText(CatalogueActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
