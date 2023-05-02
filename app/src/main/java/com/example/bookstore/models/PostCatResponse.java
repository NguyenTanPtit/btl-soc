package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class PostCatResponse {
    @SerializedName("data1")
    private Categories c;
    @SerializedName("Status")
    private int status;
    private String message;

    public Categories getC() {
        return c;
    }

    public void setC(Categories c) {
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
