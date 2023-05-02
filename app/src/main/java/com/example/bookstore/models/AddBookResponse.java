package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class AddBookResponse {
    @SerializedName("data1")
    private Book b;
    @SerializedName("Status")
    private int status;
    private String message;

    public Book getB() {
        return b;
    }

    public void setB(Book b) {
        this.b = b;
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
