package com.example.lee.projectshoeshop.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.lee.projectshoeshop.Activity.HomeActivity;
import com.example.lee.projectshoeshop.Adapter.ProductAdapter;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void getFullProduct(DatabaseReference DB, final ListView listView, final Context context){

        DB.child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Product> listProduct = new ArrayList<>();
                Product product = new Product();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    product.setId(childDataSnapshot.child("id").getValue().toString());
                    product.setName(childDataSnapshot.child("name").getValue().toString());
                    product.setCurrentPrice(Double.parseDouble(childDataSnapshot.child("currentPrice").getValue().toString()));
                    product.setOriginalPrice(Double.parseDouble(childDataSnapshot.child("originalPrice").getValue().toString()));
                    product.setSalesRate(Double.parseDouble(childDataSnapshot.child("salesRate").getValue().toString()));
                    product.setAverageRatings(Double.parseDouble(childDataSnapshot.child("averageRatings").getValue().toString()));

                    String[] image = childDataSnapshot.child("imageUrls").getValue().toString().split("\\s");

                    List<String> listimage = new ArrayList<>();
                    for (int i = 0; i < image.length; i++) {
                        listimage.add(image[i]);
                    }
                    product.setImageUrls(listimage);
                    product.setDescription(childDataSnapshot.child("description").getValue().toString());
//                    product.setPostedTime(new Date());
                    product.setCategory(childDataSnapshot.child("category").getValue().toString());
                    product.setBrand(childDataSnapshot.child("brand").getValue().toString());
                    product.setGender(childDataSnapshot.child("gender").getValue().toString());
                    String[] size = childDataSnapshot.child("size").getValue().toString().split(",");

                    List<String> listSize = new ArrayList<>();
                    for (int i = 0; i < size.length; i++) {
                        listSize.add(size[i]);
                    }

                    product.setSize(listSize);
                    product.setQuantity(Double.parseDouble(childDataSnapshot.child("quantity").getValue().toString()));
                }
                listProduct.add(product);
                ProductAdapter productAdapter = new ProductAdapter(context, R.layout.product_adapter, listProduct);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
