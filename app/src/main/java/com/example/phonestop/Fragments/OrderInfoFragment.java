package com.example.phonestop.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Models.Order;
import com.example.phonestop.Models.User;
import com.example.phonestop.R;

public class OrderInfoFragment extends Fragment {
    private User user;
    private ManagmentCart managmentCart;
    private TextView edit_name_txt;
    private TextView edit_email_txt;
    private EditText edit_address_txt;
    private EditText edit_phone_txt;
    private EditText edit_zip_txt;
    private Button order_BTN;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);
        managmentCart = ManagmentCart.getInstance(getContext().getApplicationContext());
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        user = managmentCart.getCurrentUser();
        if (user != null) {
            edit_name_txt.setText(user.getDisplayName());
            edit_email_txt.setText(user.getEmail());
            if (!user.getAddress().isEmpty()) {
                edit_address_txt.setText(user.getAddress());
            }
            if (!user.getZip_code().isEmpty()) {
                edit_zip_txt.setText(user.getZip_code());
            }
            if (!user.getPhone_number().isEmpty()) {
                edit_phone_txt.setText(user.getPhone_number());
            }
        }

        order_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name_txt.getText().toString();
                String address = edit_address_txt.getText().toString();
                String email = edit_email_txt.getText().toString();
                String phone = edit_phone_txt.getText().toString();
                String zip = edit_zip_txt.getText().toString();
                if(name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || zip.isEmpty()){
                    Toast.makeText(v.getContext().getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    user.setAddress(address);
                    user.setPhone_number(phone);
                    user.setZip_code(zip);
                    Order order = managmentCart.saveOrderToDatabase();
                    managmentCart.saveUserToDataBase();
                    showReceiptFragment(order);
                }
            }
        });
    }

    private void showReceiptFragment(Order order) {
        ReceiptFragment receiptFragment = ReceiptFragment.newInstance(order);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, receiptFragment)
                .addToBackStack(null)
                .commit();
    }


    private void findViews(View view) {
        edit_name_txt = view.findViewById(R.id.edit_name_txt);
        edit_address_txt = view.findViewById(R.id.edit_address_txt);
        edit_email_txt = view.findViewById(R.id.edit_email_txt);
        edit_phone_txt = view.findViewById(R.id.edit_phone_txt);
        edit_zip_txt = view.findViewById(R.id.edit_zip_txt);
        order_BTN = view.findViewById(R.id.order_BTN);
    }
}
