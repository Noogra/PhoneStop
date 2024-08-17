package com.example.phonestop.Data;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.phonestop.Models.Category;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;

import java.util.ArrayList;
import java.util.HashMap;


public class DataManager {
    private static ArrayList<Category> allCategoriesList;
    private static ArrayList<Category> allSubCategoriesList;
    private static ArrayList<Product> allProductsList;
    private static HashMap<Category, ArrayList<Category>> catNameToSubCatsMap;
    private static HashMap<Category, ArrayList<Product>> catToProductsMap;

    public static void initDataManager(Context context){
        allCategoriesList = new ArrayList<>();
        allSubCategoriesList = new ArrayList<>();
        allProductsList = new ArrayList<>();
        catNameToSubCatsMap = new HashMap<>();
        catToProductsMap = new HashMap<>();

        //add domain categories
        allCategoriesList.add(new Category()
                .setName("Devices")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_mobile_devices)));

        allCategoriesList.add(new Category()
                .setName("Sound")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_speakers)));

        allCategoriesList.add(new Category()
                .setName("Chargers/Power Banks")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_cables_and_chargers)));

        allCategoriesList.add(new Category()
                .setName("Protection")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_protection)));

        allCategoriesList.add(new Category()
                .setName("Data Storage")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_hard_drive)));

        allCategoriesList.add(new Category()
                .setName("Repairs")
                .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_repair)));

        //add sub categories
        for (int i = 0; i < allCategoriesList.size(); i++) {
            ArrayList<Category> tempCat = privateGetSubFromCategory(allCategoriesList.get(i).getName(), context);
            catNameToSubCatsMap.put(allCategoriesList.get(i), tempCat);
            allSubCategoriesList.addAll(tempCat);
        }
        //add products
        for (int i = 0; i < allSubCategoriesList.size(); i++) {
            ArrayList<Product>  tempProduct = privateGetProductsFromCategory(allSubCategoriesList.get(i).getName(), context);
            if(tempProduct.size() > 0){
                catToProductsMap.put(allSubCategoriesList.get(i), tempProduct);
                allProductsList.addAll(tempProduct);
            }
        }
    }

    private static ArrayList<Category> privateGetSubFromCategory(String categoryName, Context context){
        ArrayList<Category> categories = new ArrayList<>();

        switch (categoryName){

            case "Devices":
                categories.add(new Category()
                        .setName("Mobile Devices")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.samsungs))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("iPads")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_ipads))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Tablets")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_tablets))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Smart Watches")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_watches))
                        .setSubCategory(Boolean.TRUE));
                break;

            case "Sound":
                categories.add(new Category()
                        .setName("Speakers")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_speakers))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Headphones")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_headphones))
                        .setSubCategory(Boolean.TRUE));
                break;

            case "Chargers/Power Banks":
                categories.add(new Category()
                        .setName("Chargers")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_wall_chargers))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Power Banks")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_powerbank))
                        .setSubCategory(Boolean.TRUE));
                break;

            case "Protection":
                categories.add(new Category()
                        .setName("Cases")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_cases))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Screen Protect")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_screen_protector))
                        .setSubCategory(Boolean.TRUE));
                break;

            case "Data Storage":
                categories.add(new Category()
                        .setName("Flash Disk")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_flash_drivers))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Hard Disc")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.cat_hard_drive))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("Memory Card")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.subcat_memory_card))
                        .setSubCategory(Boolean.TRUE));
                break;

            case "Repairs":
                categories.add(new Category()
                        .setName("Phone Repair")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.repair_samsung))
                        .setSubCategory(Boolean.TRUE));

                categories.add(new Category()
                        .setName("iPad/Tablet repair")
                        .setDrawable_img(ContextCompat.getDrawable(context,R.drawable.tablet_repair))
                        .setSubCategory(Boolean.TRUE));
                break;
        }
        return categories;
    }

    private static ArrayList<Product> privateGetProductsFromCategory(String categoryName, Context context){
        ArrayList<Product> products = new ArrayList<>();

        switch (categoryName){

            case "Mobile Devices":
                products.add(new Product()
                        .setName("iPhone 15 Pro")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_iphone15pro.png?alt=media&token=11fa0998-aadc-4930-bb1d-b095a552117b")
                        .setDescription("The powerful iPhone 15 Pro from Apple, with a 6.1 inch Super Retina XDR screen, with a facial recognition sensor, a powerful A17 Pro main processor, three rear cameras 48+12+12 megapixels, a 12 megapixel selfie camera, wireless charging option and support for the fifth generation network 5G.")
                        .setPrice(1099.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("Galaxy S24 Ultra")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_s24_ultra.jpg?alt=media&token=510339d6-e99d-4d84-b112-39977076d23a")
                        .setDescription("A Samsung smartphone with a 6.8-inch screen, 4 rear cameras and a front selfie camera, support for the fifth generation 5G network and a fingerprint reader.")
                        .setPrice(1299.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.SAMSUNG));

                products.add(new Product()
                        .setName("One Plus 12 5G")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/product_oneplus.png?alt=media&token=171dd8f3-f535-4afa-a79a-c50de1869d88")
                        .setDescription("A quality cell phone from OnePlus, with a 6.82 inch screen, 3 rear cameras 50+64+48 megapixels and a 32 megapixel selfie camera, advanced Android system, fingerprint reader, powerful battery and supports the fifth generation 5G network.")
                        .setPrice(799.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.ONE_PLUS));
                break;
            case "iPads":
                products.add(new Product()
                        .setName("iPad Pro 13'")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_ipad_pro_13.jpg?alt=media&token=285cf5ec-70a7-4db9-8f2a-8524d912c5c2")
                        .setDescription("The iPad Pro from Apple is powerful and fast, has an Ultra Retina XDR display, a powerful Apple M4 processor, a LiDAR sensor and a facial recognition sensor, a wide range of advanced features and wide connectivity support for the ultimate iPad experience.")
                        .setPrice(1199.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("iPad Pro 11' 2024")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_ipad_pro_11_2024.jpg?alt=media&token=0febd571-d86c-4f03-b3c1-641ced17e0f6")
                        .setDescription("The iPad Pro from Apple is powerful and fast, has an Ultra Retina XDR display, a powerful Apple M4 processor, a LiDAR sensor and a facial recognition sensor, a wide range of advanced features and wide connectivity support for the ultimate iPad experience.")
                        .setPrice(999.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("iPad Pro 11' 2022")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_ipad_pro_11_2022.jpg?alt=media&token=32286d7c-56bc-46cb-a4af-ecc812c3bf02")
                        .setDescription("The iPad Pro from Apple is powerful and fast, with an 11'' Liquid Retina IPS LCD display, a powerful Apple M2 processor, a pair of 12+10 megapixel rear cameras + a LiDAR sensor and a facial recognition sensor, a 12 megapixel selfie camera, a wide variety of features Advanced and support for the fifth generation 5G network for an ultimate iPad experience.")
                        .setPrice(699.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));
                break;

            case "Tablets":
                products.add(new Product()
                        .setName("Galaxy Tab S9 Ultra")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_tab_s9_ultra.jpg?alt=media&token=4e9906b9-ce1c-480e-a635-05f26cd8c1eb")
                        .setDescription("A quality tablet from Samsung with a 14.6' screen, a pair of 13+8 megapixel rear cameras and a pair of 12+12 megapixel selfie cameras, an 8-core chipset and an advanced Android operating system. Includes S Pen.")
                        .setPrice(1299.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.SAMSUNG));

                products.add(new Product()
                        .setName("Galaxy Tab S9 FE")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_tablet_s9_fe.jpg?alt=media&token=0da7b261-61a4-4f2b-a226-eebd3801647f")
                        .setDescription("A high-quality tablet from Samsung with a 10.9\" screen, an 8 megapixel rear camera and a 12 megapixel selfie camera, an 8-core chipset, an advanced Android operating system and supports the fifth generation 5G network")
                        .setPrice(999.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));
                break;

            case "Smart Watches":
                products.add(new Product()
                        .setName("Apple Watch Ultra 2")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_apple_watch_ultra2.jpg?alt=media&token=fa291d73-b197-4d46-bad1-a771c564f754")
                        .setDescription("A smart watch for measuring fitness data from Apple that provides life documentation on the wrist and enables tracking of all your activities accurately, has a color screen with Retina LTPO OLED display and includes GPS and eSIM support.")
                        .setPrice(999.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("Galaxy Watch Ultra LTE")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_galaxy_watch_ultra.jpg?alt=media&token=973011bd-0d10-457a-91af-8e6ca0d5a197")
                        .setDescription("The Galaxy Watch Ultra LTE smartwatch from Samsung allows you to monitor your health around the clock with sleep tracking, health monitoring and a larger battery for everyday use.")
                        .setPrice(999.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.SAMSUNG));

                products.add(new Product()
                        .setName("Apple Watch 9")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_apple_watch_9.jpg?alt=media&token=66362e19-7315-46c0-a146-252bf59201dc")
                        .setDescription("A smart watch for measuring high-quality fitness data from Apple that provides life documentation on the wrist and enables accurate tracking of all your activities, has a color screen with a Retina LTPO OLED display and includes GPS support.")
                        .setPrice(299.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("Samsung Galaxy Watch 4")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_galaxy_watch_4.jpg?alt=media&token=0347e593-585b-450d-9f01-88467550ed42")
                        .setDescription("A smart watch from Samsung with a 1.4 inch screen that shows the time, displays messages, music control, pedometer and heart rate, GPS, wireless connectivity and has a wide variety of downloadable applications.")
                        .setPrice(199.99D)
                        .setType(Product.ProductType.DEVICE)
                        .setCompany(Product.ProductCompany.SAMSUNG));
                break;

            case "Speakers":

                products.add(new Product()
                        .setName("WOBURN III")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_woburn_3.jpg?alt=media&token=e28eee40-670e-42d8-a7d9-14485844d608")
                        .setDescription("The new Marshall home speaker series brings you the beloved and familiar speakers with new upgrades and significant improvements, Class D amplifiers in power ranges of 110W-60W, a triple driver array that provides deep bass and incredible clarity in the highs, and a particularly durable structure made from recycled plastic and green materials. All of these provide a strong, clear, and deep sound experience that is incomparable.")
                        .setPrice(999.99D)
                        .setType(Product.ProductType.SPEAKER)
                        .setCompany(Product.ProductCompany.MARSHALL));

                products.add(new Product()
                        .setName("Flip 6")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_jbl_flip5.jpg?alt=media&token=8bc38aa6-a63c-4f5e-9353-c22f0c3b9dd1")
                        .setDescription("JBL Flip 6 portable speaker, Bluetooth 5.1 connectivity, IP67 waterproof and a 4800mAh battery.")
                        .setPrice(99.99D)
                        .setType(Product.ProductType.SPEAKER)
                        .setCompany(Product.ProductCompany.JBL));

                break;

            case "Headphones":
                products.add(new Product()
                        .setName("Sony WH-1000XM5")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_wh1000_headphones.jpg?alt=media&token=a4f52890-bf02-49c8-b251-624436ff5343")
                        .setDescription("The Sony WH-1000XM5 provides industry-leading noise cancellation and superior sound quality for an immersive listening experience.")
                        .setPrice(399.99D)
                        .setType(Product.ProductType.HEADPHONES)
                        .setCompany(Product.ProductCompany.SONY));

                products.add(new Product()
                        .setName("Bose QuietComfort Ultra")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_bose_quitcomfort.jpg?alt=media&token=cf4a0136-59b8-4b94-bd40-fbc5b8ec6ba9")
                        .setDescription("The Bose QuietComfort headphones are renowned for their exceptional comfort and top-tier noise cancellation, perfect for uninterrupted audio enjoyment.")
                        .setPrice(449.99D)
                        .setType(Product.ProductType.HEADPHONES)
                        .setCompany(Product.ProductCompany.BOSE));
                break;

            case "Chargers":
                products.add(new Product()
                        .setName("OtterBox Wall Charger")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_otter_charger.jpg?alt=media&token=8b7efc61-ad70-4ab8-a89a-fb193e0a50a9")
                        .setDescription("Wall Charger for fast charging 20W with USB-C connection from OtterBox.")
                        .setPrice(29.99D)
                        .setType(Product.ProductType.CHARGER)
                        .setCompany(Product.ProductCompany.OTTER_BOX));

                products.add(new Product()
                        .setName("OtterBox Type-C to Type-C")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_otter_cable.jpg?alt=media&token=db9c4ebe-5536-42f6-91d6-a5fe80560b3a")
                        .setDescription("USB-C to USB-C fast charging cable, the easy choice for quick connection. The reliable cable boasts a flexible outer coating for a quality finish and is flexibly tested for durability. The fast charging cable is designed to work perfectly with OtterBox cases and accessories and is compatible with Apple, Samsung, Google and more. For a cable with high data transfer speed and fast charging power, the OtterBox fast charging cable keeps you charged without effort and wasted time.")
                        .setPrice(19.99D)
                        .setType(Product.ProductType.CABLE)
                        .setCompany(Product.ProductCompany.OTTER_BOX));
                break;

            case "Power Banks":
                products.add(new Product()
                        .setName("OtterBox 15K mAh Power Bank")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_powerbank_otter_15.png?alt=media&token=583f3208-8b62-46a8-b39b-eec001c48e0d")
                        .setDescription("The OtterBox Charge portable wireless charger is designed to charge and power your day. The battery is the perfect size, both to give you a long charging time and to facilitate convenience. USB-A ports and USB-C fast charging enable multi-device charging and secure wireless charging via Qi Wireless 10W output.")
                        .setPrice(49.99D)
                        .setType(Product.ProductType.CHARGER)
                        .setCompany(Product.ProductCompany.OTTER_BOX));

                products.add(new Product()
                        .setName("Mophie 20K mAh PowerStation XL")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_mophie_powerbank.jpg?alt=media&token=595a9fa3-2de7-4719-8498-1c9b4ec4a50a")
                        .setDescription("The mophie powerstation XL has a USB-C PD port that puts out up to 20W of fast-charging power. It also has two USB-A ports, so you can charge up to three devices at once. The compact powerstation XL has an internal 20,000mAh battery with the capacity to meet the demands of your day.")
                        .setPrice(49.99D)
                        .setType(Product.ProductType.CHARGER)
                        .setCompany(Product.ProductCompany.MOPHIE));
                break;

            case "Cases":
                products.add(new Product()
                        .setName("OtterBox Symmetry for iPhone 15 Pro")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_otterbox_case.jpg?alt=media&token=4d44f819-c565-4f73-a1e5-af6fe7f1da2e")
                        .setDescription("The Symmetry series from OtterBox presents an elegant and simple design. The cover allows you to access all the buttons of your iPhone comfortably while protecting against bumps and drops with raised edges.")
                        .setPrice(49.99D)
                        .setType(Product.ProductType.CASE)
                        .setCompany(Product.ProductCompany.OTTER_BOX));

                products.add(new Product()
                        .setName("OtterBox Symmetry for s24 Ultra")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_otter_case_24ultra.jpg?alt=media&token=ed6456e5-08a8-465c-9312-d42a8d2493d2")
                        .setDescription("The Symmetry series from OtterBox presents an elegant and simple design. The cover allows you to access all the buttons of your iPhone comfortably while protecting against bumps and drops with raised edges.")
                        .setPrice(49.99D)
                        .setType(Product.ProductType.CASE)
                        .setCompany(Product.ProductCompany.OTTER_BOX));

                products.add(new Product()
                        .setName("Original case for OnePlus 12")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_case_oneplus12.png?alt=media&token=ed463e44-adc6-472d-b909-674566a225b1")
                        .setDescription("Simple and clean original case fron OnePlus.")
                        .setPrice(39.99D)
                        .setType(Product.ProductType.CASE)
                        .setCompany(Product.ProductCompany.ONE_PLUS));

                products.add(new Product()
                        .setName("OtterBox Defender for iPad Pro 13'")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_ipad_case.jpg?alt=media&token=0c24d45e-02d7-43a1-afe8-2313cfdd2cf8")
                        .setDescription("Defender cover from OtterBox for iPad Pro 13 Inch (M4) Protective cover for iPad (10th generation) that protects against drops, dirt and scratches. The efficient rugged design consists of a hard inner shell, a soft outer non-slip cover and a built-in screen protector. The stand has four positions that allow comfortable typing and viewing and does double work as a touch screen protector.")
                        .setPrice(99.99D)
                        .setType(Product.ProductType.CASE)
                        .setCompany(Product.ProductCompany.OTTER_BOX));
                break;

            case "Screen Protect":
                products.add(new Product()
                        .setName("Glass Screen Protector")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_iphone_glass_prot.jpg?alt=media&token=8b56a14c-0d16-4e0a-ae10-95ebd338695f")
                        .setDescription("Glass Screen Protector for iPhone 15 Pro.")
                        .setPrice(14.99D)
                        .setType(Product.ProductType.SCREEN_PROTECTOR)
                        .setCompany(Product.ProductCompany.OTTER_BOX));

                products.add(new Product()
                        .setName("Silicon Screen Protector")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_s24_screen_prot.jpg?alt=media&token=30801d4c-e44b-4ea1-ac04-0fa6a77f938c")
                        .setDescription("Silicon Screen Protector for S24 Ultra.")
                        .setPrice(29.99D)
                        .setType(Product.ProductType.SCREEN_PROTECTOR)
                        .setCompany(Product.ProductCompany.ZAGG));
                break;

            case "Flash Disk":
                products.add(new Product()
                        .setName("SanDisk Phone Drive Apple Lightning")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_flash_iphone.jpg?alt=media&token=aa0f54b9-7f1c-4991-8226-2c9d8bc40283")
                        .setDescription("High-quality portable memory from SanDisk that has a Lightning connection for Apple products on one side and a USB connection on the other side, with a fast USB 3.2 interface that allows easy and fast file transfer between Apple devices and connected computers.")
                        .setPrice(49.99D)
                        .setType(Product.ProductType.MEMORY_STORAGE)
                        .setCompany(Product.ProductCompany.SANDISK));

                products.add(new Product()
                        .setName("SanDisk Cruzer Ultra USB 3.0")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_simple_flash.jpg?alt=media&token=4c3f7535-2e3c-41ea-9cfd-b5fb0155b436")
                        .setDescription("Portable memory from SanDisk is extremely fast with a USB 3.0 connection and a huge memory capacity of 512GB.")
                        .setPrice(39.99D)
                        .setType(Product.ProductType.MEMORY_STORAGE)
                        .setCompany(Product.ProductCompany.SANDISK));
                break;

            case "Hard Disc":
                products.add(new Product()
                        .setName("Sandisk Extreme 500GB USB 3.2")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/cat_hard_drive.jpg?alt=media&token=d0dbbe30-1072-44f9-8cc4-7f7839f897f6")
                        .setDescription("Portable and high-quality external hard drive in SSD technology from Sandisk with a fast USB 3.2 type C connection and a capacity of 500GB.")
                        .setPrice(99.99D)
                        .setType(Product.ProductType.MEMORY_STORAGE)
                        .setCompany(Product.ProductCompany.SANDISK));
                break;

            case "Memory Card":
                products.add(new Product()
                        .setName("SanDisk Extreme A2 MicroSDXC")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_memory_card.jpg?alt=media&token=60570ccb-1bc4-4841-9bb6-8ae19676d5b3")
                        .setDescription("MicroSDXC memory card with a volume of 256GB, UHS-I interface, read speed up to 190MB/s, write speed up to 130MB/s, class Class 10, U3, V30, A2.")
                        .setPrice(29.99D)
                        .setType(Product.ProductType.MEMORY_STORAGE)
                        .setCompany(Product.ProductCompany.SANDISK));
                break;

            case "Phone Repair":
                products.add(new Product()
                        .setName("iPhone 15 Pro Original Screen")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_iphone_15_screen.jpg?alt=media&token=d852940d-6109-44e8-95b9-8110e766374e")
                        .setDescription("100% Original Apple Screen, pricing includes the replacement.")
                        .setPrice(399.99D)
                        .setType(Product.ProductType.REPAIR)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("Galaxy S24 Ultra Original Screen")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_s24_screen.jpg?alt=media&token=20c9b1b2-ca55-4887-a740-e276eef5142a")
                        .setDescription("100% Original Samsung Screen, pricing includes the replacement.")
                        .setPrice(349.99D)
                        .setType(Product.ProductType.REPAIR)
                        .setCompany(Product.ProductCompany.SAMSUNG));

                products.add(new Product()
                        .setName("OnePlus 12 Original Screen")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_oneplus_screen.jpg?alt=media&token=2b7ff9f2-9cbb-41e3-82db-c563eaac9d82")
                        .setDescription("100% Original OnePlus Screen, pricing includes the replacement.")
                        .setPrice(199.99D)
                        .setType(Product.ProductType.REPAIR)
                        .setCompany(Product.ProductCompany.ONE_PLUS));
                break;

            case "iPad/Tablet repair":
                products.add(new Product()
                        .setName("iPad Pro 13' Original Screen")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_ipad_screen.jpg?alt=media&token=5d85b2a3-9bc7-4258-9245-a170b57939a0")
                        .setDescription("100% Original Apple Screen, pricing includes the replacement.")
                        .setPrice(399.99D)
                        .setType(Product.ProductType.REPAIR)
                        .setCompany(Product.ProductCompany.APPLE));

                products.add(new Product()
                        .setName("S9 Ultra Tablet Original Screen")
                        .setImg_url("https://firebasestorage.googleapis.com/v0/b/phonestop-9efbc.appspot.com/o/prod_s9_ultra_screen.jpg?alt=media&token=8e4b4536-12a0-4a00-9a5a-e4e84511481c")
                        .setDescription("100% Original Samsung Screen, pricing includes the replacement.")
                        .setPrice(399.99D)
                        .setType(Product.ProductType.REPAIR)
                        .setCompany(Product.ProductCompany.SAMSUNG));
                break;
        }

        return products;
    }

    public static ArrayList<Category> getSubCategoriesFromCategory(Category category){
        return catNameToSubCatsMap == null ? null : catNameToSubCatsMap.get(category);
    }
    public static ArrayList<Product> getProductsFromCategory(Category category){
        return catToProductsMap == null ? null : catToProductsMap.get(category);
    }

    public static ArrayList<Category> getDomainCategories(){
            return allCategoriesList;
    }
    public static ArrayList<Category> getAllSubCategories(){
            return allSubCategoriesList;
    }
    public static ArrayList<Product> getAllProducts(){
        return allProductsList;
    }

}



