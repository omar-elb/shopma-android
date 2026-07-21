package com.shopma.presentation.panier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shopma.R;
import com.shopma.domain.model.CartItem;

import java.util.List;
import java.util.Locale;

public class PanierAdapter extends BaseAdapter {

    public interface OnQuantityChangeListener {
        void onDecrease(CartItem item);
    }

    private final Context context;
    private final List<CartItem> items;
    private final OnQuantityChangeListener listener;

    public PanierAdapter(Context context, List<CartItem> items, OnQuantityChangeListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override public int getCount() { return items.size(); }
    @Override public CartItem getItem(int position) { return items.get(position); }
    @Override public long getItemId(int position) { return items.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_panier, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvItemTitle);
            holder.tvQuantity = convertView.findViewById(R.id.tvItemQuantity);
            holder.tvPrice = convertView.findViewById(R.id.tvItemPrice);
            holder.btnDecrease = convertView.findViewById(R.id.btnDecrease);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvQuantity.setText(context.getString(R.string.quantity_format, item.getQuantity()));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%.2f$", item.getTotal()));
        holder.btnDecrease.setOnClickListener(v -> listener.onDecrease(item));

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle, tvQuantity, tvPrice;
        Button btnDecrease;
    }
}
