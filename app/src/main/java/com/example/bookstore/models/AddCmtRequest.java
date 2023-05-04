package com.example.bookstore.models;

import com.google.gson.annotations.SerializedName;

public class AddCmtRequest {
    @SerializedName("id")
    private String bookID;
    private Comments comment;

    public AddCmtRequest(String bookID, Comments comment) {
        this.bookID = bookID;
        this.comment = comment;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public Comments getComment() {
        return comment;
    }

    public void setComment(Comments comment) {
        this.comment = comment;
    }
}
