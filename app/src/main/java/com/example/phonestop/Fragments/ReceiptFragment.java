package com.example.phonestop.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phonestop.Models.Order;
import com.example.phonestop.R;

public class ReceiptFragment extends Fragment {
    private static final String ARG_ORDER = "order";
    private TextView receipt_name_txt;
    private TextView receipt_phone_txt;
    private TextView receipt_email_txt;
    private TextView receipt_address_txt;
    private TextView receipt_order_id_txt;
    private TextView receipt_total_price_txt;
    private TextView receipt_date_txt_id;
    private Order order;

    public static ReceiptFragment newInstance(Order order) {
        ReceiptFragment fragment = new ReceiptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ORDER, order); // Use putParcelable if using Parcelable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = getArguments().getParcelable(ARG_ORDER); // Use getParcelable if using Parcelable
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_receipt, container, false);
        findViews(v);
        initViews();
        return v;
    }

    private void findViews(View v) {
        receipt_name_txt = v.findViewById(R.id.receipt_name_txt);
        receipt_phone_txt = v.findViewById(R.id.receipt_phone_txt);
        receipt_email_txt = v.findViewById(R.id.receipt_email_txt);
        receipt_address_txt = v.findViewById(R.id.receipt_address_txt);
        receipt_order_id_txt = v.findViewById(R.id.receipt_order_id_txt);
        receipt_total_price_txt = v.findViewById(R.id.receipt_total_price_txt);
        receipt_date_txt_id = v.findViewById(R.id.receipt_date_txt_id);
    }

    private void initViews() {
        if (order != null) {
            receipt_name_txt.setText(order.getUser().getDisplayName());
            receipt_phone_txt.setText(order.getUser().getPhone_number());
            receipt_email_txt.setText(order.getUser().getEmail());
            receipt_address_txt.setText(order.getUser().getAddress());
            receipt_order_id_txt.setText(order.getId());
            receipt_total_price_txt.setText(String.valueOf(order.getTotal_price()) + " nis");
            receipt_date_txt_id.setText(order.getDate());
        }
    }
}