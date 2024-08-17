package com.example.phonestop.Models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;


public class Category implements Parcelable {
    private String name = "";
    private Drawable drawable_img = null;
    private boolean isSubCategory = false;


    protected Category(Parcel in) {
        name = in.readString();
        isSubCategory = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isSubCategory ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public Category(){
    }

    public Drawable getDrawable_img() {
        return drawable_img;
    }

    public Category setDrawable_img(Drawable drawable_img) {
        this.drawable_img = drawable_img;
        return this;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public Category setSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
        return this;
    }
}
