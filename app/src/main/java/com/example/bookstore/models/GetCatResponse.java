package com.example.bookstore.models;

import java.util.List;

public class GetCatResponse {
    private List<Categories> list;

    public List<Categories> getList() {
        return list;
    }

    public void setList(List<Categories> list) {
        this.list = list;
    }
}
