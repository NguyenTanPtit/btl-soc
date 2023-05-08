package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class Comments {
    private String _id;
    @SerializedName("UserId")
    private String userID;
    private String content;
    private String date;

    public Comments(String userID, String content, String date) {
        this.userID = userID;
        this.content = content;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
