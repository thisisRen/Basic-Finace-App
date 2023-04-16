package com.example.sqlite.model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private int id;
    private String title;
    private String category;
    private String price;
    private String date;

    public Item(int id, String title, String category, String price, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    public Item(String title, String category, String price, String date) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
