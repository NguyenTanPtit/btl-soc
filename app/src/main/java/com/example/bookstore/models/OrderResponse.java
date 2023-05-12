package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("Status")
    private int status;
    private String message;
    private String link;
    @SerializedName("data1")
    private Order order;

    public OrderResponse(int status, String message, String link, Order order) {
        this.status = status;
        this.message = message;
        this.link = link;
        this.order = order;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
