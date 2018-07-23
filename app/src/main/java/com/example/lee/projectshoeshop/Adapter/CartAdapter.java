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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Entity.CartItem;
import com.example.lee.projectshoeshop.Entity.Product;
import com.example.lee.projectshoeshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private int resource;
    private List<CartItem> arrCart;
    private FirebaseAuth mAuth;
    private DatabaseReference DB;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<CartItem> arrCart) {
        super(context, resource, arrCart);
        this.context = context;
        this.resource = resource;
        this.arrCart = arrCart;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageP = (ImageView) convertView.findViewById(R.id.imageP);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            viewHolder.txtQuantity = (TextView) convertView.findViewById(R.id.txtQuantity);
            viewHolder.btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = (Button) convertView.findViewById(R.id.btnPlus);
            viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btnRemove);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CartItem cartItem = arrCart.get(position);
        String image = cartItem.getImage();
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://shoeshopdb.appspot.com").child(image + ".PNG");

        try {
            final File localFile = File.createTempFile("images", "jpg");
            firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    viewHolder.imageP.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}

        viewHolder.txtName.setText(cartItem.getName());
        viewHolder.txtPrice.setText(cartItem.getPrice()+"$");
        viewHolder.txtQuantity.setText(cartItem.getQuantity()+"");

        mAuth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshopdb.firebaseio.com/");
        FirebaseUser cUser = mAuth.getCurrentUser();
        final String userID = cUser.getUid();

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.child("cart").child(userID).child(cartItem.getId()).removeValue();
            }
        });

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartItem.getQuantity() > 0){
                    DB.child("cart").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(cartItem.getId())){
                                String quantity = dataSnapshot.child(cartItem.getId()).child("quantity").getValue().toString();
                                double quan = Double.parseDouble(quantity);
                                double price = cartItem.getPrice()/quan;
                                quan = quan-1;
                                DB.child("cart").child(userID).child(cartItem.getId()).child("quantity").setValue(quan);
                                DB.child("cart").child(userID).child(cartItem.getId()).child("price").setValue(quan*price);
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
        });

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.child("cart").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(cartItem.getId())){
                            String quantity = dataSnapshot.child(cartItem.getId()).child("quantity").getValue().toString();
                            double quan = Double.parseDouble(quantity);
                            double price = cartItem.getPrice()/quan;
                            quan = quan+1;
                            DB.child("cart").child(userID).child(cartItem.getId()).child("quantity").setValue(quan);
                            DB.child("cart").child(userID).child(cartItem.getId()).child("price").setValue(quan*price);
                        }else{
                            DB.child("cart").child(userID).child(cartItem.getId()).setValue(cartItem);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return convertView;
    }

    public class ViewHolder{
        ImageView imageP;
        TextView txtName, txtPrice, txtQuantity;
        Button btnMinus, btnPlus, btnRemove;
    }

}
