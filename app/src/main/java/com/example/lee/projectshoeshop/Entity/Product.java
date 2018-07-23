package com.example.lee.projectshoeshop.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements Serializable{

    private String id;
    private String name = "";
    private String description = "";
    private Date postedTime = new Date();
    private String category;
    private String brand;
    private String gender;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> size;
    private double currentPrice = 0;
    private double originalPrice = 0;
    private Double averageRatings = null;
    private double salesRate = 0;
    private double quantity;
    public Product() {
    }

    public Product(String id, String name, String category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;

    }

    public Product(String id, String name, String description, Date postedTime, String category,
                   String brand, String gender, List<String> imageUrls, List<String> size,
                   double currentPrice, double originalPrice, Double averageRatings,
                   double salesRate, double quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postedTime = postedTime;
        this.category = category;
        this.brand = brand;
        this.gender = gender;
        this.imageUrls = imageUrls;
        this.size = size;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.averageRatings = averageRatings;
        this.salesRate = salesRate;
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public double getCurrentPrice() {
        if(getSalesRate() > 0){
            return getOriginalPrice()* getSalesRate();
        }
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getAverageRatings() {
        return averageRatings;
    }

    public void setAverageRatings(Double averageRatings) {
        this.averageRatings = averageRatings;
    }

    public double getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(double salesRate) {
        this.salesRate = salesRate;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", postedTime=" + postedTime +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrls=" + imageUrls +
                ", size=" + size +
                ", currentPrice=" + currentPrice +
                ", originalPrice=" + originalPrice +
                ", averageRatings=" + averageRatings +
                ", salesRate=" + salesRate +
                ", quantity=" + quantity +
                '}';
    }
}
