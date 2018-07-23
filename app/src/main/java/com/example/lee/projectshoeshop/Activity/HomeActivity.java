package com.example.lee.projectshoeshop.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lee.projectshoeshop.Adapter.ProductAdapter;
import com.example.lee.projectshoeshop.DAO.ProductDAO;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private TextView txtName;
    private TextView txtEmail;
    private ImageView avatar;
    private ListView listProduct;
    private FloatingActionButton cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName1);
        txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmail1);

        View v = (View) drawer.findViewById(R.id.homelayout).findViewById(R.id.hLayout);
        listProduct = (ListView) v.findViewById(R.id.viewProduct);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        DatabaseReference DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/");

        ProductDAO productDAO = new ProductDAO();

        productDAO.getFullProduct(DB, listProduct, this);

        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product =(Product) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("data", product);
                startActivity(intent);
            }
        });

        View getAppBar = (View) drawer.findViewById(R.id.homelayout);
        cart = (FloatingActionButton) getAppBar.findViewById(R.id.fab);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_accountsetting) {

        } else if (id == R.id.nav_signout) {
            signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            txtName.setText(getString(R.string.google_status_fmt, user.getDisplayName()));
           txtEmail.setText(getString(R.string.google_status_fmt, user.getEmail()));

           try {
               Glide.with(this)
                       .load(user.getPhotoUrl())
                       .apply(RequestOptions.circleCropTransform())
                       .into(avatar);
           }catch (Exception e){
               Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
           }
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);

        }
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
    }
}
