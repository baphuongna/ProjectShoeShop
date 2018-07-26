package com.example.lee.projectshoeshop.Activity;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Adapter.ViewPageAdapter;
import com.example.lee.projectshoeshop.Entity.Cart;
import com.example.lee.projectshoeshop.Entity.CartItem;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView txtName,txtSales, txtPrice, txtSize,txtBrand, txtGender, txtDescription;
    private Spinner spinerSize;
    private Button btnAddCart;
    private Product product;
    private FirebaseAuth mAuth;
    private DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        product = (Product)getIntent().getExtras().getSerializable("data");
        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this,product.getImageUrls());
        viewPager.setAdapter(viewPageAdapter);

        txtName = (TextView) findViewById(R.id.txtName2);
        txtSales = (TextView) findViewById(R.id.txtSales2);
        txtPrice = (TextView) findViewById(R.id.txtPrice2);
        txtSize = (TextView) findViewById(R.id.txtSize2);
        txtBrand = (TextView) findViewById(R.id.txtBrand2);
        txtGender = (TextView) findViewById(R.id.txtGender2);
        txtDescription = (TextView) findViewById(R.id.txtDescription2);

        spinerSize = (Spinner) findViewById(R.id.spinerSize2);
        btnAddCart = (Button) findViewById(R.id.btnAddCart2);

        txtName.setText(product.getName());
        txtSales.setText(product.getSalesRate()+"%");
        txtPrice.setText(product.getCurrentPrice()+"$");

        txtBrand.setText(product.getBrand());
        txtGender.setText(product.getGender());
        txtDescription.setText(product.getDescription());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, product.getSize());
        spinerSize.setAdapter(adapter);
  //      spinerSize.setOnScrollChangeListener();
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void addToCart(){
        mAuth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/");
        FirebaseUser cUser = mAuth.getCurrentUser();
        final String userID = cUser.getUid();

        final CartItem cartItem = new CartItem();
        cartItem.setId(product.getId());
        cartItem.setName(product.getName());
        cartItem.setImage(product.getImageUrls().get(0));
        double newprice = product.getOriginalPrice() - ((product.getOriginalPrice()*product.getSalesRate())/100);
        cartItem.setPrice(newprice);
        cartItem.setQuantity(1);
        DB.child("cart").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(cartItem.getId())){
                    String quantity = dataSnapshot.child(cartItem.getId()).child("quantity").getValue().toString();
                    double quan = Double.parseDouble(quantity);
                    quan = quan+1;
                    DB.child("cart").child(userID).child(cartItem.getId()).child("quantity").setValue(quan);
                    DB.child("cart").child(userID).child(cartItem.getId()).child("price").setValue(quan*cartItem.getPrice());
                }else{
                    DB.child("cart").child(userID).child(cartItem.getId()).setValue(cartItem);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
