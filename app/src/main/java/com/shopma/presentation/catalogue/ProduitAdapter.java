package com.shopma.presentation.catalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shopma.R;
import com.shopma.domain.model.Product;

import java.util.List;
import java.util.Locale;

public class ProduitAdapter extends BaseAdapter {

    private final Context context;
    private final List<Product> products;

    public ProduitAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override public int getCount() { return products.size(); }
    @Override public Product getItem(int position) { return products.get(position); }
    @Override public long getItemId(int position) { return products.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.ivProductImage);
            holder.tvTitle = convertView.findViewById(R.id.tvProductTitle);
            holder.tvCategory = convertView.findViewById(R.id.tvProductCategory);
            holder.tvPrice = convertView.findViewById(R.id.tvProductPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag(); // recyclage — exigence du prof
        }

        Product product = products.get(position);
        holder.tvTitle.setText(product.getTitle());
        holder.tvCategory.setText(product.getCategory());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%.2f$", product.getPrice()));

        Glide.with(context).load(product.getImageUrl())
                .placeholder(R.drawable.bg_logo_circle).into(holder.ivImage);

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvCategory, tvPrice;
    }
}
