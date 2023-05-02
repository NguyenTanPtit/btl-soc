package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {
    @SerializedName("data1")
    private List<Book> list;
    @SerializedName("Status")
    private int status;
    private String message;

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

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}
