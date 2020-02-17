package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.startForgotPasswordEnterEmailFragment();
    }



    private void startForgotPasswordEnterEmailFragment() {
        Fragment fragment = new ForgotPasswordEnterEmailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutForgotPassword, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }







}
