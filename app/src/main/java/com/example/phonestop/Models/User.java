package com.example.phonestop.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {
    private String displayName = "";
    private String email = "";
    private String address = "";
    private String zip_code = "";
    private String phone_number = "";
    private String uid = "";

    public User() {
    }

    protected User(Parcel in) {
        displayName = in.readString();
        email = in.readString();
        address = in.readString();
        zip_code = in.readString();
        phone_number = in.readString();
        uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(zip_code);
        dest.writeString(phone_number);
        dest.writeString(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getZip_code() {
        return zip_code;
    }

    public User setZip_code(String zip_code) {
        this.zip_code = zip_code;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public User setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

}
