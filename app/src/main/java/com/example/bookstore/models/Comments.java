package com.example.bookstore.models;

public class Comments {
    private String id;
    private User user;
    private String content;
    private String date;

    public Comments(String id, User user, String content, String date) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public Comments(User user, String content, String date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
