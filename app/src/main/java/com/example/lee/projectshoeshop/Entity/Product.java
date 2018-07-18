package com.example.lee.projectshoeshop.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    /**
     * Product ID.
     */
    private String id;
    /**
     * Product name.
     */
    private String name = "";
    /**
     * Product description.
     */
    private String description = "";
    /**
     * The time that the product is posted.
     */
    private Date postedTime = new Date();
    /**
     * Product category.
     */
    private String category;
    /**
     * Product brand.
     */
    private String brand;
    /**
     * Product gender.
     */
    private String gender;
    /**
     * Product images.
     */
    private List<String> imageUrls = new ArrayList<>();
    /**
     * The size of the product.
     */
    private double size = 1;
    /**
     * Whether the product is available in the inventory.
     */
    private boolean available = false;
    /**
     * The current price of the product. It should be less than or equal to the original price.
     */
    private double currentPrice = 0;
    /**
     * The original price of the product.
     */
    private double originalPrice = 0;
    /**
     * The average ratings of the product, or null if there is no review.
     * This is a redundant field for easier product searching and sorting.
     */
    private Double averageRatings = null;
    private double salesRate = 0;
    public Product() {
    }

    public Product(String id, String name, String category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;

    }

    public Product(String id, String name, String description, Date postedTime, String category,
                   String brand, String gender, List<String> imageUrls, double size,
                   boolean available, double currentPrice, double originalPrice,
                   Double averageRatings, Double salesRate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postedTime = postedTime;
        this.category = category;
        this.brand = brand;
        this.gender = gender;
        this.imageUrls = imageUrls;
        this.size = size;
        this.available = available;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.averageRatings = averageRatings;
        this.salesRate = salesRate;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
}
