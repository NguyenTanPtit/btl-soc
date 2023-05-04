package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class GetCartRes {
//    @SerializedName("data1")
//    private Cart c;
    @SerializedName("Status")
    private int status;
    private String message;
}
