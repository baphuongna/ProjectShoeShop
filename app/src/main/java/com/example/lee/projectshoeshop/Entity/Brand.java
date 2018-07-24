package com.example.lee.projectshoeshop.Entity;

import android.net.Uri;
import android.os.Parcel;

import java.io.Serializable;

/**
 * Brand of the shoes.
 */
public class Brand implements Serializable {
    /**
     * Brand ID.
     */
    private String id;

    /**
     * Brand name.
     */
    private String name;

    /**
     * Brand logo.
     */
    private String imageUrl;

    public Brand() {

    }

    public Brand(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
