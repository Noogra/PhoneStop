package com.example.phonestop;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.phonestop.Interface.OnUserUpdatedListener;
import com.example.phonestop.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditUserFragment extends Fragment {
    private User user;
    private FirebaseUser firebaseUser;
    private EditText edit_name_txt;
    private EditText edit_email_txt;
    private EditText edit_password_txt;
    private Button edit_save_BTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_user, container, false);

        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
        }
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        findViews(v);
        initViews();


        return v;
    }

    private void initViews() {
        if(user.getName() != ""){
            edit_name_txt.setText(user.getName());

        }
        if(user.getEmail() != ""){
            edit_email_txt.setText(user.getEmail());

        }
        if(user.getPassword() != ""){
            edit_password_txt.setText(user.getPassword());
        }


        edit_save_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail, newName, newPassword;
                newEmail = edit_email_txt.getText().toString().trim();
                newName = edit_name_txt.getText().toString().trim();
                newPassword = edit_password_txt.getText().toString().trim();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(newEmail, newPassword);

                firebaseUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User re-authenticated.");
                            }
                        });


                firebaseUser.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }
                            }
                        });

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();

                firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });

                firebaseUser.updateEmail(newEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.");
                                }
                            }
                        });

                user.setEmail(newEmail);
                user.setName(newName);
                user.setPassword(newPassword);

                getActivity().getSupportFragmentManager().popBackStack();
                if (getTargetFragment() instanceof OnUserUpdatedListener) {
                    ((OnUserUpdatedListener) getTargetFragment()).onUserUpdated(user);
                }
            }
        });
    }

    private void findViews(View v) {
        edit_name_txt = v.findViewById(R.id.edit_name_txt);
        edit_email_txt = v.findViewById(R.id.edit_email_txt);
        edit_password_txt = v.findViewById(R.id.edit_password_txt);
        edit_save_BTN = v.findViewById(R.id.edit_save_BTN);
    }

    private static final String ARG_USER = "user";


    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }
}