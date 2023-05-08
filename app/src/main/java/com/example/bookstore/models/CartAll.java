package com.example.bookstore.models;

import java.util.List;

public class CartAll {
    private String UserId;
    private List<Products> products;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
