package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class AddCartRes {
    @SerializedName("data1")
    private CartAll c;
    @SerializedName("Status")
    private int status;
    private String message;

    public CartAll getC() {
        return c;
    }

    public void setC(CartAll c) {
        this.c = c;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

