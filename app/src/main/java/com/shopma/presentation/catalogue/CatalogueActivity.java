package com.shopma.presentation.catalogue;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CatalogueActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY = "com.shopma.EXTRA_CATEGORY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("CatalogueActivity — à construire");
        tv.setPadding(48, 48, 48, 48);
        setContentView(tv);
    }
}
