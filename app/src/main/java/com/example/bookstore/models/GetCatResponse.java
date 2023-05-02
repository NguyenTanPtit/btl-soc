package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCatResponse {
    @SerializedName("data1")
    private List<Categories> list;
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

    public List<Categories> getList() {
        return list;
    }

    public void setList(List<Categories> list) {
        this.list = list;
    }
}
