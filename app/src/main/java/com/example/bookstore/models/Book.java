package com.example.bookstore.models;

import java.util.List;

public class Book {
    private String id;
    private String name, author, imgUrl, datePublish, description;
    private int pageNumber, buyNumber; // số trang sách và số lượng mua
    private float rate;
    private List<Comments> comments;

    public Book(String id, String name, String author, String imgUrl, String datePublish,
                String description, int pageNumber, int buyNumber, float rate, List<Comments> comments) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.imgUrl = imgUrl;
        this.datePublish = datePublish;
        this.description = description;
        this.pageNumber = pageNumber;
        this.buyNumber = buyNumber;
        this.rate = rate;
        this.comments = comments;
    }

    public Book(String name, String author, String imgUrl, String datePublish, String description,
                int pageNumber, int buyNumber, float rate, List<Comments> comments) {
        this.name = name;
        this.author = author;
        this.imgUrl = imgUrl;
        this.datePublish = datePublish;
        this.description = description;
        this.pageNumber = pageNumber;
        this.buyNumber = buyNumber;
        this.rate = rate;
        this.comments = comments;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
