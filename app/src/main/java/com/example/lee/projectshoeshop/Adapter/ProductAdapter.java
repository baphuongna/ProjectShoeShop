package com.example.lee.projectshoeshop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iconProduct = (ImageView) convertView.findViewById(R.id.iconProduct);
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
        List<String> image = product.getImageUrls();
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://shoeshopdb.appspot.com").child(image.get(1)+".PNG");

        try {
            final File localFile = File.createTempFile("images", "jpg");
            firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    viewHolder.iconProduct.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}

        viewHolder.txtName.setText(product.getName());
        viewHolder.txtPrice.setText(product.getCurrentPrice()+"VND");
        viewHolder.txtSales.setText(product.getSalesRate()+"%");
        viewHolder.txtOPrice.setText(product.getOriginalPrice()+"VND");
        viewHolder.pRating.setRating(Float.valueOf(product.getAverageRatings().toString()));

        return convertView;
    }

    public class ViewHolder {
        ImageView iconProduct;
        TextView txtName, txtPrice, txtSales, txtOPrice;
        RatingBar pRating;
    }

}
