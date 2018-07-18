package com.example.lee.projectshoeshop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private int resource;
    private List<Product> arrProduct;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> arrProduct) {
        super(context, resource, arrProduct);
        this.context = context;
        this.resource = resource;
        this.arrProduct = arrProduct;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon_only);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            viewHolder.txtSales = (TextView) convertView.findViewById(R.id.txtSales);
            viewHolder.txtOPrice = (TextView) convertView.findViewById(R.id.txtOPrice);
            viewHolder.pRating = (RatingBar) convertView.findViewById(R.id.pRating);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = arrProduct.get(position);
//        viewHolder.icon.setImageURI(product.getImageUrls().get(0));
        viewHolder.txtName.setText(product.getName());
        viewHolder.txtPrice.setText(product.getCurrentPrice()+"$");
        viewHolder.txtSales.setText(product.getSalesRate()+"%");
        viewHolder.txtOPrice.setText(product.getOriginalPrice()+"$");
        //viewHolder.pRating.setRating(Float.valueOf(product.getAverageRatings().toString()));

        return convertView;
    }

    public class ViewHolder {
        ImageView icon;
        TextView txtName, txtPrice, txtSales, txtOPrice;
        RatingBar pRating;
    }

}
