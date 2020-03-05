package com.example.servemesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.helper.MD5;
import com.example.servemesystem.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ForgotPasswordNewPasswordFragment extends Fragment {
    private Button buttonBack, buttonSubmit;
    private EditText editPassword, editConfirmPassword;
    private User user;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public ForgotPasswordNewPasswordFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordNewPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordNewPasswordFragment fragment = new ForgotPasswordNewPasswordFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password_new_password, container, false);
        this.getUserDetails();
        this.setAllEditTexts(view);
        this.setAllButtons(view);
        return view;
    }

    private void getUserDetails() {
        this.user = (User)getArguments().getSerializable("user");
    }

    private void setAllEditTexts(View view) {
        this.editPassword = view.findViewById(R.id.editPassword);
        this.editConfirmPassword = view.findViewById(R.id.editConfirmPassword);
    }

    private void setAllButtons(View view) {
        this.defineAllButtons(view);
        this.setAllButtonListener(view);
    }


    private void defineAllButtons(View view) {
        this.buttonBack = view.findViewById(R.id.buttonBack);
        this.buttonSubmit = view.findViewById(R.id.buttonSubmit);
    }

    private void setAllButtonListener(View view) {
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            private String password, confirmPassword;

            @Override
            public void onClick(View view) {
                this.getNewPasswordValue();
                if(this.isPasswordFormatValid()) {
                    this.requestServerForPasswordChange();
                }
            }

            private boolean isPasswordFormatValid() {
                if(password.length() == 0){
                    editPassword.setError(getString(R.string.error_required_field_password));
                    editPassword.requestFocus();
                    return false;
                }

                if(confirmPassword.length() == 0){
                    editConfirmPassword.setError(getString(R.string.error_required_field_confirm_password));
                    editConfirmPassword.requestFocus();
                    return false;
                }

                if(!(password.length() >= 8 && password.matches("[a-zA-Z0-9!@$%^&*()]+"))) {
                    editPassword.setError(getString(R.string.error_format_password));
                    editPassword.requestFocus();
                    return false;
                }

                if(!password.equals(confirmPassword)){
                    editConfirmPassword.setError(getString(R.string.error_passwords_unmatch));
                    editConfirmPassword.requestFocus();
                    return false;
                }

                return  true;
            }

            private void startLoginActivity() {
                Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(intentLogin);
                getActivity().finish();
            }

            private void getNewPasswordValue() {
                password = editPassword.getText().toString();
                confirmPassword = editConfirmPassword.getText().toString();

            }

            private void requestServerForPasswordChange() {
                mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                performPasswordChange();

                            }
                        });
            }

            private void performPasswordChange() {
                AuthCredential credential = EmailAuthProvider.getCredential(
                        user.getEmail(), user.getPassword());
                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                firebaseUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(MD5.getMd5(password))
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        mAuth.signOut();
                                                        Toast.makeText(getContext(), getString(R.string.message_forgot_password_successful), Toast.LENGTH_LONG).show();
                                                        startLoginActivity();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }

    });
    }
}

/*
vids
2
3
4
5
6
7
8

ppt
chap 3 pg 5 9 10 35 36 37 39 43 46 49 63
agile notes all 3 pages
scrum framework ppt all pgs

 */

