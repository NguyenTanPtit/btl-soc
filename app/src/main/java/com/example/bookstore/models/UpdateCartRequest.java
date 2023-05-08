package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class UpdateCartRequest {
    @SerializedName("UserId")
    private String uID;

    @SerializedName("type")
    private String type;

    @SerializedName("productId")
    private String productID;

    public UpdateCartRequest(String uID, String type, String productID) {
        this.uID = uID;
        this.type = type;
        this.productID = productID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
