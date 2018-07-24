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
//    private static final ArrayList<Product> listProduct = null;

    public void getFullProduct(DatabaseReference DB, final ListView listView, final Context context){
       // DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/shoeshopdb");

//        readData(DB, new MyCallback() {
//            @Override
//            public void onCallback(Product value) {
//                listProduct.add(value);
//                Log.v("", listProduct.size()+"---------------------------1--------------------------");
//            }
//        });
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
//        Log.v("", listProduct.size()+"-----------------------------2------------------------");
//        return listProduct;
    }
    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

//
//    public void readData(DatabaseReference DB, final MyCallback myCallback) {
//        DB.child("product").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Product product = new Product();
//                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    product.setId(childDataSnapshot.child("id").getValue().toString());
//                    product.setName(childDataSnapshot.child("name").getValue().toString());
//                    Log.v("", childDataSnapshot.getKey()+ " Value : " + childDataSnapshot.child("name").getValue().toString());
//                }
////
////                    product.setId(dataSnapshot.child("p01").child("id").getValue().toString());
////                    product.setName(dataSnapshot.child("p01").child("name").getValue().toString());
////                    product.setDescription(dataSnapshot.child("p01").child("description").getValue().toString());
////                    product.setPostedTime(new Date());
////                    product.setCategory(dataSnapshot.child("p01").child("category").getValue().toString());
////                    product.setBrand(dataSnapshot.child("p01").child("brand").getValue().toString());
////                    product.setGender(dataSnapshot.child("p01").child("gender").getValue().toString());
////
////                    List<String> listimage = new ArrayList<>();
////                    dataSnapshot.child("p01").child("imageUrls").getValue().toString();
////                    String[] image = dataSnapshot.child("p01").child("imageUrls").getValue().toString().split(",");
////                    listimage.add(image[0]);
////                    product.setImageUrls(listimage);
//
////                    product.setSize(Double.parseDouble(dataSnapshot.child("p01").child("size").getValue().toString()));
////                    product.setAvailable(true);
////                    product.setCurrentPrice(Double.parseDouble(dataSnapshot.child("p01").child("currentPrice").getValue().toString()));
////                    product.setOriginalPrice(Double.parseDouble(dataSnapshot.child("p01").child("originalPrice").getValue().toString()));
////                    product.setAverageRatings(Double.parseDouble(dataSnapshot.child("p01").child("averageRatings").getValue().toString()));
////                    product.setSalesRate(Double.parseDouble(dataSnapshot.child("p01").child("salesRate").getValue().toString()));
//                listProduct.add(product);
//                myCallback.onCallback(product);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public interface MyCallback {
//        void onCallback(Product value);
//    }

}
