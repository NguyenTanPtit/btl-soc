package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCartRes {
    @SerializedName("data1")
    private List<CartAll> c;
    @SerializedName("Status")
    private int status;
    private String message;

//    public CartAll getC() {
//        return c;
//    }
//
//    public void setC(CartAll c) {
//        this.c = c;
//    }

    public List<CartAll> getC() {
        return c;
    }

    public void setC(List<CartAll> c) {
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
