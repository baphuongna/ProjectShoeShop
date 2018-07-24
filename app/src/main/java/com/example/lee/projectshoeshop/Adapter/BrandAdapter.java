package com.example.lee.projectshoeshop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lee.projectshoeshop.Entity.Brand;
import com.example.lee.projectshoeshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BrandAdapter extends ArrayAdapter<Brand> {

    private Context context;
    private int resource;
    private List<Brand> arrBrand;

    public BrandAdapter(@NonNull Context context, int resource, @NonNull List<Brand> arrBrand) {
        super(context, resource, arrBrand);
        this.context = context;
        this.resource = resource;
        this.arrBrand = arrBrand;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_gallery_brand, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgBrand = (ImageView) convertView.findViewById(R.id.imgBrand);
            viewHolder.txtBName = (TextView) convertView.findViewById(R.id.txtBName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Brand brand = arrBrand.get(position);
        String image = brand.getImageUrl();
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://shoeshopdb.appspot.com").child(image);

            try {
                final File localFile = File.createTempFile("images", "jpg");
                firebaseStorage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        viewHolder.imgBrand.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            } catch (IOException e ) {}

        viewHolder.txtBName.setText(brand.getName());
        viewHolder.txtBName.setGravity(Gravity.CENTER);

        return convertView;
    }

    public class ViewHolder {
        ImageView imgBrand;
        TextView txtBName;
    }
}
