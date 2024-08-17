package com.example.phonestop.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Models.Product;
import com.example.phonestop.R;
import com.example.phonestop.Utilities.ImageLoader;


public class ProductInfoFragment extends Fragment {
    private Product product;
    private TextView product_info_name_txt_id;
    private ImageView product_info_img_id;
    private TextView product_info_descr_txt_id;
    private TextView product_info_price_txt_id;
    private Button add_to_cart_BTN;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_info, container, false);
        findViews(v);
        initViews();

        return v;
    }

    private void initViews() {
        product_info_name_txt_id.setText(product.getName());
        ImageLoader.getInstance().load(product.getImg_url(), product_info_img_id);
        product_info_descr_txt_id.setText(product.getDescription());
        product_info_price_txt_id.setText(product.getPrice().toString() + " nis");

        add_to_cart_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagmentCart managmentCart = ManagmentCart.getInstance(v.getContext().getApplicationContext());
                int currNumber = product.getNumberinCart();
                product.setNumberinCart(currNumber + 1);
                managmentCart.insertProduct(product);
            }
        });
    }

    private void findViews(View v) {
        product = (Product) getArguments().getParcelable(ARG_PRODUCT_INFO);
        if(product.getCompany() != null){
            product_info_name_txt_id = v.findViewById(R.id.product_info_name_txt_id);
            product_info_img_id = v.findViewById(R.id.product_info_img_id);
            product_info_descr_txt_id = v.findViewById(R.id.product_info_descr_txt_id);
            product_info_price_txt_id = v.findViewById(R.id.product_info_price_txt_id);
            add_to_cart_BTN = v.findViewById(R.id.add_to_cart_BTN);
        }

    }

    private static final String ARG_PRODUCT_INFO = "product_info";

    public static ProductInfoFragment newInstance(Product product) {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT_INFO, product);
        fragment.setArguments(args);
        return fragment;
    }
}