package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderResponse {
    @SerializedName("data1")
    private List<Order> list;
    @SerializedName("Status")
    private int status;
    private String message;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
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
