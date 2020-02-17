package com.example.servemesystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ForgotPasswordEnterEmailFragment extends Fragment {


    public ForgotPasswordEnterEmailFragment() {
        // Required empty public constructor
    }


    public static ForgotPasswordEnterEmailFragment newInstance(String param1, String param2) {
        ForgotPasswordEnterEmailFragment fragment = new ForgotPasswordEnterEmailFragment();
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
        View view =  inflater.inflate(R.layout.fragment_forgot_password_enter_email, container, false);

        return view;
    }


}
