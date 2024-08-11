package com.example.phonestop.Models;

public class Product {
    public enum ProductType{
        NULL,
        DEVICE,
        SPEAKER,
        CHARGER,
        CASE,
        REPAIR,
        CABLE,
        SCREEN_PROTECTOR,
        MEMORY_STORAGE
    }
    public enum ProductCompany{
        NULL,
        APPLE,
        SAMSUNG,
        MARSHALL,
        OTTER_BOX,
        JBL,
        ZAGG
    }

    public ProductCompany getCompany() {
        return company;
    }

    public Product setCompany(ProductCompany company) {
        this.company = company;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public ProductType getType() {
        return type;
    }

    public Product setType(ProductType type) {
        this.type = type;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public Product setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        return this;
    }

    private ProductCompany company = ProductCompany.NULL;
    private String description = "";
    private String name = "";
    private ProductType type = ProductType.NULL;
    private Float price = 0.0F;
    private Integer id = 0;

    @Override
    public String toString() {
        return "Product{" +
                "company=" + company +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    public Product(){
    }
}