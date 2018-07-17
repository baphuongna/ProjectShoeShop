package com.example.lee.projectshoeshop.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lee.projectshoeshop.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class AndressActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView txtAndress;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andress);
        txtAndress = (TextView) findViewById(R.id.txtAndress);
        txtAndress.setText("Địa Chỉ :  KM29 Đại Lộ Thăng Long, Thạch Hoà, Thạch Thất, Khu GD&ĐT, Khu CNC Hòa Lạc Hà Nội 155300");
        initializeMap();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void initializeMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng storeLocation = new LatLng(90,90);
        googleMap.addMarker(new MarkerOptions().position(storeLocation).title("Shop"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(storeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);
    }
}
