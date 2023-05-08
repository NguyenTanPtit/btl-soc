package com.example.bookstore.models;

public class Cart {
    private String UserId;
    private Products products;

    public Cart(String userId, Products products) {
        UserId = userId;
        this.products = products;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}

