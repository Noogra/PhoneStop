package com.example.phonestop.Models;
import java.util.HashMap;

public class Category {
    private HashMap<Integer, Product> allProducts = new HashMap<>();
    private String name = "";
    public String img = "";

    public String getImg() {
        return img;
    }

    public Category setImg(String img) {
        this.img = img;
        return this;
    }


    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public HashMap<Integer, Product> getAllProducts() {
        return allProducts;
    }

    public Category setAllProducts(HashMap<Integer, Product> allProducts) {
        this.allProducts = allProducts;
        return this;
    }

    public Category(){
    }

}
