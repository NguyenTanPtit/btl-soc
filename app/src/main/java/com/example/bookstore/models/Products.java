package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Products implements Serializable {
    private String productId;
    private String productName;
    private String productImg,des;
    private float productPrice;
    private int quantity ;

    public Products(String productId, String productName, String productImg, float productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public Products(String productId, String productName, String productImg, String des, float productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.des = des;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
