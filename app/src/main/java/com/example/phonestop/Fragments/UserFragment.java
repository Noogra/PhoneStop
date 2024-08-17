package com.example.phonestop.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.phonestop.Activity.LoginActivity;
import com.example.phonestop.Models.User;
import com.example.phonestop.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFragment extends Fragment{
    private User user;
    private FirebaseUser firebaseUser;
    private TextView user_name_txt;
    private TextView user_email_txt;
    private Button log_out_BTN;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        findViews(v);
        initViews();


        return v;


    }
    private void initViews() {
        if (firebaseUser != null) {
            user = new User()
                    .setName(firebaseUser.getDisplayName())
                    .setEmail(firebaseUser.getEmail())
                    .setUid(firebaseUser.getUid());

            user_name_txt.setText(user.getName());
            user_email_txt.setText(user.getEmail());
        }
        log_out_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getContext().getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent i = new Intent(getContext(), LoginActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });
            }
        });
    }

    private void findViews(View v) {
        user_name_txt = v.findViewById(R.id.user_name_txt);
        user_email_txt = v.findViewById(R.id.user_email_txt);
        log_out_BTN = v.findViewById(R.id.log_out_BTN);

    }

 }