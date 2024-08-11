package com.example.phonestop.Data;

import com.example.phonestop.Models.Category;
import com.example.phonestop.R;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class DataManager {
    public Integer product_id = 0;
    public static ArrayList<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<>();

        categories.add(new Category()
                .setName("Mobile Devices")
                .setImg("https://www.folio3.com/mobile/wp-content/uploads/2022/08/PngItem_291349.png"));

        categories.add(new Category()
                .setName("Speakers")
                .setImg("https://images.bauerhosting.com/affiliates/sites/8/2024/03/Untitled-design-2023-08-16T101039.157.jpg?ar=16%3A9&fit=crop&crop=top&auto=format&w=1440&q=80"));

        categories.add(new Category()
                .setName("Chargers and Cables")
                .setImg("https://imageio.forbes.com/specials-images/imageserve/5efb2db6222b100007dd101c/Otterbox-Fast-Charge-lineup/960x0.jpg?format=jpg&width=960"));

        categories.add(new Category()
                .setName("Protection")
                .setImg("https://m.media-amazon.com/images/I/71K9e3hcINL._AC_UF894,1000_QL80_.jpg"));

        categories.add(new Category()
                .setName("Data Storage")
                .setImg("https://www.westerndigital.com/content/dam/store/en-us/assets/promotions/sandisk/best-sellers/best-sellers-sd.png.thumb.1280.1280.png"));

        categories.add(new Category()
                .setName("Repairs")
                .setImg("https://t4.ftcdn.net/jpg/06/57/59/17/360_F_657591708_1KC26Erazxm9sgNADjviZeAZHtrhlM7b.jpg"));

        return categories;
    }



}
