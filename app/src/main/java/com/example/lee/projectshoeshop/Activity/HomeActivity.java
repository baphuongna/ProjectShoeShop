package com.example.lee.projectshoeshop.Activity;

import android.content.Intent;
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
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lee.projectshoeshop.Adapter.BrandAdapter;
import com.example.lee.projectshoeshop.Adapter.ProductAdapter;
import com.example.lee.projectshoeshop.Adapter.ProductShowAdapter;
import com.example.lee.projectshoeshop.DAO.ProductDAO;
import com.example.lee.projectshoeshop.Entity.Brand;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private TextView txtName;
    private TextView txtEmail;
    private ImageView avatar;
    private GridView listProductProduct;
    private GridView listProductMan;
    private GridView listProductWomen;
    private GridView listProductKids;
    private GridView gridViewBrand;
    private FloatingActionButton cart;
    private GridView listBrand;
    private DatabaseReference DB;
    private View v;
    private ViewStub viewStubHome;
    private ViewStub viewStubProduct;
    private ViewStub viewStubBrand;
    private BrandAdapter brandAdapter;
    private ProductShowAdapter productShowAdapter;

    private int currentMode = 0;

    private static final int HOME_MODE = 0;
    private static final int BRAND_MODE = 1;
    private static final int PRODUCT_MAN_MODE = 2;
    private static final int PRODUCT_WOMAN_MODE = 3;
    private static final int PRODUCT_KIDS_MODE = 4;
    private static final int PRODUCT_BRAND_MODE = 5;
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

        v = (View) drawer.findViewById(R.id.homelayout).findViewById(R.id.hLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        txtName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtName1);
        txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmail1);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/");

        View getAppBar = (View) drawer.findViewById(R.id.homelayout);
        cart = (FloatingActionButton) getAppBar.findViewById(R.id.fab);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        viewStubHome = (ViewStub) v.findViewById(R.id.viewStub);
        viewStubProduct = (ViewStub) v.findViewById(R.id.viewStub2);
        viewStubBrand = (ViewStub) v.findViewById(R.id.viewStub3);

        viewStubHome.inflate();
        viewStubProduct.inflate();
        viewStubBrand.inflate();

        listBrand = (GridView) v.findViewById(R.id.gBrand);
        listProductMan = (GridView) v.findViewById(R.id.gMan);
        listProductWomen = (GridView) v.findViewById(R.id.gWomen);
        listProductKids = (GridView) v.findViewById(R.id.gKids);
        listProductProduct = (GridView) v.findViewById(R.id.gridViewProduct);
        gridViewBrand = (GridView) v.findViewById(R.id.gridViewBrand);

        listProductMan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPro(adapterView, i);
            }
        });

        listProductWomen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPro(adapterView, i);
            }
        });

        listProductProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPro(adapterView, i);
            }
        });

        listProductKids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPro(adapterView, i);
            }
        });

        listBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = (Brand) adapterView.getItemAtPosition(i);
                currentMode = PRODUCT_BRAND_MODE;
                switchView(brand.getName());
            }
        });

        gridViewBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = (Brand) adapterView.getItemAtPosition(i);
                currentMode = PRODUCT_BRAND_MODE;
                switchView(brand.getName());
            }
        });

        switchView("");

    }

    private void viewPro(AdapterView<?> adapterView,int i){
        Product product =(Product) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
        intent.putExtra("data", product);
        startActivity(intent);
    }


    private void switchView(String brand){
        if(currentMode == HOME_MODE){
            viewStubHome.setVisibility(View.VISIBLE);
            viewStubBrand.setVisibility(View.GONE);
            viewStubProduct.setVisibility(View.GONE);
        }else if(currentMode == BRAND_MODE){
            viewStubHome.setVisibility(View.GONE);
            viewStubBrand.setVisibility(View.VISIBLE);
            viewStubProduct.setVisibility(View.GONE);
        }else{
            viewStubHome.setVisibility(View.GONE);
            viewStubBrand.setVisibility(View.GONE);
            viewStubProduct.setVisibility(View.VISIBLE);
        }
        setAdapter(brand);
    }

    private void setAdapter(String brand){
        if(currentMode == HOME_MODE){
            initBrand("home");
            initProduct("Men");
            initProduct("Women");
            initProduct("Kids");
        }else if(currentMode == BRAND_MODE){
            initBrand("");
        }else if(currentMode == PRODUCT_MAN_MODE){
            initProduct("AllMen");
        }else if(currentMode == PRODUCT_WOMAN_MODE){
            initProduct("AllWomen");
        }else if(currentMode == PRODUCT_KIDS_MODE){
            initProduct("AllKids");
        }else if(currentMode == PRODUCT_BRAND_MODE){
            initProduct(brand);
        }else{
            initProduct("");
        }
    }

    private void initProduct(final String gender){
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

                if(gender.contains(product.getGender()) && gender.contains("Men")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductMan.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getGender()) && gender.contains("Women")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductWomen.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getGender()) && gender.contains("Kids")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductKids.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getGender()) && gender.contains("AllMen")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductProduct.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getGender()) && gender.contains("AllWomen")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductProduct.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getGender()) && gender.contains("AllKids")){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductProduct.setAdapter(productShowAdapter);
                }else if(gender.contains(product.getBrand())){
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductProduct.setAdapter(productShowAdapter);
                }else{
                    listProduct.add(product);
                    productShowAdapter = new ProductShowAdapter(HomeActivity.this, R.layout.product_adapter_gridview, listProduct);
                    listProductProduct.setAdapter(productShowAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initBrand(final String layout){
        DB.child("brand").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int limit = 3;
                if(layout.contains("home")){
                    limit = 3;
                }else{
                    limit = Integer.MAX_VALUE;
                }
                ArrayList<Brand> listB = new ArrayList<>();
                int i = 1;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Brand brand =  new Brand();
                    brand.setId(childDataSnapshot.child("id").getValue().toString());
                    brand.setName(childDataSnapshot.child("name").getValue().toString());
                    brand.setImageUrl(childDataSnapshot.child("imageUrl").getValue().toString());
                    if(i > limit){
                        break;
                    }else {
                        listB.add(brand);
                    }
                    i++;
                }

                if(layout.contains("home")){
                    brandAdapter = new BrandAdapter(HomeActivity.this, R.layout.activity_gallery_brand, listB);
                    listBrand.setAdapter(brandAdapter);
                }else{
                    brandAdapter = new BrandAdapter(HomeActivity.this, R.layout.activity_gallery_brand, listB);
                    gridViewBrand.setAdapter(brandAdapter);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
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
            currentMode = HOME_MODE;
        } else if (id == R.id.nav_brand) {
            currentMode = BRAND_MODE;
        } else if (id == R.id.nav_forman) {
            currentMode = PRODUCT_MAN_MODE;
        } else if (id == R.id.nav_forwoman) {
            currentMode = PRODUCT_WOMAN_MODE;
        } else if (id == R.id.nav_forkids) {
            currentMode = PRODUCT_KIDS_MODE;
        } else if (id == R.id.nav_accountsetting) {

        } else if (id == R.id.nav_signout) {
            signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
        switchView("");
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
//               Bitmap bitmap = BitmapFactory.decodeFile(user.getPhotoUrl().getEncodedPath());
//               avatar.setImageBitmap(bitmap);

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
        mAuth.signOut();
    }
}
