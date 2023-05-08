package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteCartRequest {
    @SerializedName("UserId")
    String uID;

    @SerializedName("dsId")
    List<String> productId;

    public DeleteCartRequest(String uID, List<String> productId) {
        this.uID = uID;
        this.productId = productId;
    }
}
