package com.example.lee.projectshoeshop.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Adapter.ViewPageAdapter;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;

import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView txtName,txtSales, txtPrice, txtSize,txtBrand, txtGender, txtDescription;
    private Spinner spinerSize;
    private Button btnAddCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Product  product = (Product)getIntent().getExtras().getSerializable("data");
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
        txtSize.setText(product.getSize()+"");
        txtBrand.setText(product.getBrand());
        txtGender.setText(product.getGender());
        txtDescription.setText(product.getDescription());



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
