package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {
    @SerializedName("data1")
    private List<Book> list;

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}
