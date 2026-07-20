package com.shopma.presentation.panier;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PanierActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("PanierActivity — à construire");
        tv.setPadding(48, 48, 48, 48);
        setContentView(tv);
    }
}
