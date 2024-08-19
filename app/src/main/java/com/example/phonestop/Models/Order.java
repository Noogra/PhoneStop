package com.example.phonestop.Models;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Order implements Parcelable {
    private User user;
    private ArrayList<Product> products;
    private String id;
    private double total_price;
    private Date date;

    public Order(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
        this.date = Calendar.getInstance().getTime();
    }

    protected Order(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        products = in.createTypedArrayList(Product.CREATOR);
        id = in.readString();
        total_price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeTypedList(products);
        dest.writeString(id);
        dest.writeDouble(total_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public double getTotal_price() {
        return total_price;
    }

    public Order setTotal_price(double total_price) {
        this.total_price = total_price;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String formattedTime = dateFormat.format(date);
        return formattedTime;
    }

    public Order setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Order setProducts(ArrayList<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", products=" + products +
                ", id=" + id +
                ", date=" + date +
                '}';
    }

}
