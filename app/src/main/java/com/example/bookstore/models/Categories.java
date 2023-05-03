package com.example.bookstore.models;

public class Categories {
    private String imgUrl;
    private String name;

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public Categories(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }
}
