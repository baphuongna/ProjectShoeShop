package com.example.lee.projectshoeshop.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Adapter.CartAdapter;
import com.example.lee.projectshoeshop.Entity.CartItem;
import com.example.lee.projectshoeshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ListView listCart;
    private Button btnCheckOut;
    private TextView txtTotalPrice;
    private FirebaseAuth mAuth;
    private DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/");

        final ListView listCart = (ListView) findViewById(R.id.listCart);
        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        txtTotalPrice = (TextView) findViewById(R.id.txtTotalPrice);

        DB.child("cart").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CartItem> arrCart = new ArrayList<>();
                CartItem cartItem = new CartItem();
                double total = 0;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    cartItem.setId(childDataSnapshot.child("id").getValue().toString());
                    cartItem.setName(childDataSnapshot.child("name").getValue().toString());
                    cartItem.setQuantity(Double.parseDouble(childDataSnapshot.child("quantity").getValue().toString()));
                    cartItem.setImage(childDataSnapshot.child("image").getValue().toString());
                    cartItem.setPrice(Double.parseDouble(childDataSnapshot.child("price").getValue().toString()));
                    total += Double.parseDouble(childDataSnapshot.child("price").getValue().toString());
                }
                arrCart.add(cartItem);
                CartAdapter cartAdapter = new CartAdapter(CartActivity.this, R.layout.cart_adapter, arrCart);
                listCart.setAdapter(cartAdapter);
                txtTotalPrice.setText(total+"$");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
