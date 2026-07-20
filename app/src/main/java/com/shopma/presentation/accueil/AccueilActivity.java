package com.shopma.presentation.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shopma.R;
import com.shopma.data.local.SessionManager;
import com.shopma.presentation.catalogue.CatalogueActivity;
import com.shopma.presentation.panier.PanierActivity;
import com.shopma.presentation.pointsretrait.PointsRetraitActivity;
import com.shopma.presentation.profil.ProfilActivity;

public class AccueilActivity extends AppCompatActivity {

    public static final String EXTRA_USER_NAME = "com.shopma.EXTRA_USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        SessionManager sessionManager = new SessionManager(this);
        String userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        if (userName == null) userName = sessionManager.getName();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText(getString(R.string.greeting_format, userName));

        setupCategoryCards();
    }

    private void setupCategoryCards() {
        findViewById(R.id.cardAllProducts).setOnClickListener(v -> openCatalogue(null));
        findViewById(R.id.cardElectronics).setOnClickListener(v -> openCatalogue("electronics"));
        findViewById(R.id.cardJewelery).setOnClickListener(v -> openCatalogue("jewelery"));
        findViewById(R.id.cardMenClothing).setOnClickListener(v -> openCatalogue("men's clothing"));
        findViewById(R.id.cardWomenClothing).setOnClickListener(v -> openCatalogue("women's clothing"));
        findViewById(R.id.cardPickupPoints).setOnClickListener(v ->
                startActivity(new Intent(this, PointsRetraitActivity.class)));
    }

    private void openCatalogue(String category) {
        Intent intent = new Intent(this, CatalogueActivity.class);
        if (category != null) intent.putExtra(CatalogueActivity.EXTRA_CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accueil, menu);

        menu.findItem(R.id.action_cart).getActionView()
                .findViewById(R.id.cartBadgeContainer)
                .setOnClickListener(v -> startActivity(new Intent(this, PanierActivity.class)));

        menu.findItem(R.id.action_profile).setOnMenuItemClickListener(item -> {
            startActivity(new Intent(this, ProfilActivity.class));
            return true;
        });

        return true;
    }
}
