package com.example.servemesystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ForgotPasswordEnterOTPFragment extends Fragment {
    private EditText editOtp;
    private Button buttonBack, buttonSubmit;


    public ForgotPasswordEnterOTPFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordEnterOTPFragment newInstance(String param1, String param2) {
        ForgotPasswordEnterOTPFragment fragment = new ForgotPasswordEnterOTPFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_enter_ot, container, false);
        this.setEditText(view);
        this.setAllButtons(view);
        return view;
    }

    private void setEditText(View view) {
        this.editOtp = view.findViewById(R.id.editOtp);
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
            private String otp;
            @Override
            public void onClick(View view) {
                otp = editOtp.getText().toString();
                if(!otp.isEmpty()) {
                    this.requestServerForOtpVerification();
                    startFragment(new ForgotPasswordNewPasswordFragment());
                }
                else{
                    editOtp.setError(getString(R.string.error_required_field_otp));
                    editOtp.requestFocus();
                }
            }

            private void requestServerForOtpVerification() {
                //
            }
        });
    }

    private void startFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutForgotPassword, fragment);
        transaction.commit();
    }


}
