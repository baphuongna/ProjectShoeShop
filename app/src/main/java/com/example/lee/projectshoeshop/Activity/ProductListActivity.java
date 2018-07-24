package com.example.lee.projectshoeshop.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lee.projectshoeshop.Adapter.ProductAdapter;
import com.example.lee.projectshoeshop.DAO.ProductDAO;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private DatabaseReference DB;
    private ListView listProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
//        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Product Search");

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        listProduct = (ListView) findViewById(R.id.listProduct);
        DB = FirebaseDatabase.getInstance().getReference();

        ProductDAO productDAO = new ProductDAO();
        productDAO.getFullProduct(DB, listProduct, this);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ProductDAO productDAO = new ProductDAO();
                productDAO.getFullProduct(DB, listProduct, ProductListActivity.this);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if(newText != null && !newText.isEmpty()){
                    DB.child("product").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Product> listNameProduct = new ArrayList<>();
                            Product product = new Product();
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                String namePro = childDataSnapshot.child("name").getValue().toString();
                                if(namePro.contains(newText)){
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
                            }
                            if(product.getName() != ""){
                                listNameProduct.add(product);
                                ProductAdapter productAdapter = new ProductAdapter(ProductListActivity.this, R.layout.product_adapter, listNameProduct);
                                listProduct.setAdapter(productAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    ProductDAO productDAO = new ProductDAO();
                    productDAO.getFullProduct(DB, listProduct, ProductListActivity.this);
                }
                return true;
            }
        });

        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product =(Product) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("data", product);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
