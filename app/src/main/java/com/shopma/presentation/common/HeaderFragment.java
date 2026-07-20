package com.shopma.presentation.common;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shopma.R;

public class HeaderFragment extends Fragment {

    private static final String ARG_USER_NAME = "arg_user_name";
    private TextView tvCartCount;

    public static HeaderFragment newInstance(String userName) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        TextView tvGreeting = view.findViewById(R.id.tvGreeting);
        tvCartCount = view.findViewById(R.id.tvCartCount);

        String userName = getArguments() != null ? getArguments().getString(ARG_USER_NAME, "") : "";
        tvGreeting.setText(getString(R.string.greeting_format, userName));
        updateCartCount(0);

        return view;
    }

    public void updateCartCount(int count) {
        if (tvCartCount != null) {
            tvCartCount.setText(getString(R.string.cart_items_format, count));
        }
    }
}
