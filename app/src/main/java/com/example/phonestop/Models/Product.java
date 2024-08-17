package com.example.phonestop.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public enum ProductType{
        NULL,
        DEVICE,
        HEADPHONES,
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
        BOSE,
        SONY,
        MARSHALL,
        OTTER_BOX,
        MOPHIE,
        JBL,
        SANDISK,
        ZAGG,
        ONE_PLUS
    }

    private ProductCompany company = ProductCompany.NULL;
    private ProductType type = ProductType.NULL;
    private String description = "";
    private String name = "";
    private Double price = 0.0D;
    private String img_url = null;
    private int numberinCart;

    protected Product(Parcel in) {
        description = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        img_url = in.readString();
        numberinCart = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(name);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeString(img_url);
        dest.writeInt(numberinCart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public ProductCompany getCompany() {
        return company;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public Product setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
        return this;
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

    public String getImg_url() {
        return img_url;
    }

    public Product setImg_url(String img_url) {
        this.img_url = img_url;
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

    public Double getPrice() {
        return price;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "company=" + company +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }

    public Product(){
    }
}