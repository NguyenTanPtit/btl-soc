package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    private String userId, phone, address;
    @SerializedName("products")
    private List<Products> list;

    public Order(String userId, String phone, String address, List<Products> list) {
        this.userId = userId;
        this.phone = phone;
        this.address = address;
        this.list = list;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Products> getList() {
        return list;
    }

    public void setList(List<Products> list) {
        this.list = list;
    }
}
