package com.shopma.presentation.profil;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("ProfilActivity — à construire");
        tv.setPadding(48, 48, 48, 48);
        setContentView(tv);
    }
}
