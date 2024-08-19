package com.example.phonestop.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonestop.Activity.LoginActivity;
import com.example.phonestop.Helper.ManagmentCart;
import com.example.phonestop.Models.Order;
import com.example.phonestop.Models.User;
import com.example.phonestop.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFragment extends Fragment {
    private User user;
    private TextView user_name_txt;
    private TextView user_email_txt;
    private EditText user_address_txt;
    private EditText edit_user_txt_zip;
    private EditText edit_user_phone_id;
    private Button save_BTN_user_info;
    private Button log_out_BTN;
    private ManagmentCart managmentCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        managmentCart = ManagmentCart.getInstance(getContext().getApplicationContext());
        findViews(v);
        initViews();


        return v;


    }

    private void initViews() {
        user = managmentCart.getCurrentUser();
        if (user != null) {
            user_name_txt.setText(user.getDisplayName());
            user_email_txt.setText(user.getEmail());

            if (!user.getAddress().isEmpty()) {
                user_address_txt.setText(user.getAddress());
            }
            if (!user.getZip_code().isEmpty()) {
                edit_user_txt_zip.setText(user.getZip_code());
            }
            if (!user.getPhone_number().isEmpty()) {
                edit_user_phone_id.setText(user.getPhone_number());
            }
        }
        save_BTN_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = user_address_txt.getText().toString();
                String phone = edit_user_phone_id.getText().toString();
                String zip = edit_user_txt_zip.getText().toString();
                user.setAddress(address);
                user.setPhone_number(phone);
                user.setZip_code(zip);
                managmentCart.saveUserToDataBase();
                Toast.makeText(v.getContext().getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });
        log_out_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    AuthUI.getInstance()
                            .signOut(requireContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    logOutAndClearData();
                                }
                            });
                }
            }
        });
    }

    private void findViews(View v) {
        user_name_txt = v.findViewById(R.id.user_name_txt);
        user_email_txt = v.findViewById(R.id.user_email_txt);
        log_out_BTN = v.findViewById(R.id.log_out_BTN);
        user_address_txt = v.findViewById(R.id.user_address_txt);
        edit_user_txt_zip = v.findViewById(R.id.edit_user_txt_zip);
        edit_user_phone_id = v.findViewById(R.id.edit_user_phone_id);
        save_BTN_user_info = v.findViewById(R.id.save_BTN_user_info);

    }

    private void logOutAndClearData() {
        // Clear the cart data
        managmentCart.clear();

        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Start LoginActivity and clear the activity stack
                        Intent loginIntent = new Intent(requireActivity(), LoginActivity.class);
                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(loginIntent);

                        // Finish the parent activity (MainActivity)
                        requireActivity().finish();
                    }
                });
    }

}