package com.training.starthub.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.training.starthub.R;
import com.training.starthub.ui.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        }

        // Get the current product
        Product currentProduct = productList.get(position);

        // Bind data to the layout elements
        ImageView productImage = convertView.findViewById(R.id.product_favorite_image);
        TextView productName = convertView.findViewById(R.id.product_favorite_name);
        TextView productCategory = convertView.findViewById(R.id.product_favorite_category);

        // Set the data
        productName.setText(currentProduct.getName());
        productCategory.setText(currentProduct.getCategory());

        // Load image using Glide or Picasso
        Glide.with(context).load(currentProduct.getImageUrl()).into(productImage);

        return convertView;
    }
}
