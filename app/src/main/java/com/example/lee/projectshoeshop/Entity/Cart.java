package com.example.lee.projectshoeshop.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Cart implements Serializable {
    private String cartId;
    private List<CartItem> products;
    private double total;
    private Date date;

    public Cart() {
    }

    public Cart(String cartId, List<CartItem> products, double total, Date date) {
        this.cartId = cartId;
        this.products = products;
        this.total = total;
        this.date = date;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", products=" + products +
                ", total=" + total +
                ", date=" + date +
                '}';
    }
}
